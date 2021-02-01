package com.kuqi.maill.common.core.lock;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.Order;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 20:25
 **/
@Aspect
@Component
@Order(100)
@Slf4j
public class OptimisticRetryAOP implements InitializingBean {

    // @annotation 标识具体方法，@within 标识具体类
    @Pointcut("@annotation(OptimisticRetry)||@within(OptimisticRetry)")
    public void optimisticRetryPointcut() {

    }

    @Around("optimisticRetryPointcut()")
    public Object doConcurrentOperation(ProceedingJoinPoint joinPoint) throws Throwable {

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        // 代理目标对象Class
        Class targetClazz = joinPoint.getTarget().getClass();
        Method method = targetClazz.getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        // 乐观锁重试
        Optional<OptimisticRetry> optimisticRetryOption = getOptimisticRetry(method.getAnnotations());
        if (!optimisticRetryOption.isPresent()) {

            if (log.isDebugEnabled()) {
                log.debug("no optimisticRetry annotation to execute!");
            }
            return joinPoint.proceed();
        }
        OptimisticRetryConfig optimisticRetryConfig = obtainOptimisticRetryConfig(optimisticRetryOption.get());
        int numAttempts = 0;
        OptimisticLockingFailureException lockFailureException;
        long startTime = System.currentTimeMillis();
        do {
            numAttempts++;
            try {
                return joinPoint.proceed();
            } catch (OptimisticLockingFailureException ex) {

                lockFailureException = ex;
                long executeTime = System.currentTimeMillis() - startTime;
                long maxExecuteTime = optimisticRetryConfig.getMaxExecuteTime();

                if (log.isDebugEnabled()) {
                    log.debug("execute optimistic retry lock!count {},time {}!max try count {},max execute time {}!", numAttempts, maxExecuteTime,
                            optimisticRetryConfig.getMaxTryCount(), maxExecuteTime);
                }

                // 乐观锁重试时间限制
                if (isLargerThanMaxExecuteTime(executeTime, maxExecuteTime)) {

                    // 超过乐观锁时间长度控制，抛出乐观锁异常
                    log.warn("throw optimistic locking failure exception!num attempts [{}],start time [{}]," +
                                    "actual execute time [{}] ms, max execute time [{}] ms",
                            numAttempts, startTime, executeTime, maxExecuteTime);
                    throw lockFailureException;
                }
            }
        }
        // 乐观锁重试次数限制
        while (numAttempts <= optimisticRetryConfig.getMaxTryCount());

        // 超过乐观锁重试次数，抛出乐观锁异常
        log.warn("throw optimistic locking failure exception!num attempts {} ", numAttempts);
        throw lockFailureException;
    }

    /**
     * 大于限制的重试执行时间
     *
     * @param executeTime
     * @return
     */
    private boolean isLargerThanMaxExecuteTime(long executeTime, long maxExecuteTime) {

        if (executeTime <= 0) {
            return false;
        }
        if (executeTime > maxExecuteTime) {
            return true;
        }
        return false;
    }

    private OptimisticRetryConfig obtainOptimisticRetryConfig(OptimisticRetry optimisticRetry) {

        // 从注解中获取配的值
        OptimisticRetryConfig optimisticRetryConfig = new OptimisticRetryConfig();
        optimisticRetryConfig.setMaxTryCount(optimisticRetry.value());
        optimisticRetryConfig.setMaxExecuteTime(optimisticRetry.maxExecuteTime());
        return optimisticRetryConfig;
    }

    private Optional<OptimisticRetry> getOptimisticRetry(Annotation[] annotations) {

        if (ArrayUtils.isEmpty(annotations)) {
            return Optional.empty();
        }
        for (Annotation anno : annotations) {
            if (anno.annotationType().getName().equals(OptimisticRetry.class.getName())) {
                return Optional.of((OptimisticRetry) anno);
            }
        }
        return Optional.empty();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("init optimistic retry lock!");
    }

    /**
     * 重试配置
     */
    @Data
    private static class OptimisticRetryConfig implements Serializable {

        private static final long serialVersionUID = 1162101189394542750L;
        /**
         * 最大重试次数
         */
        private int maxTryCount;

        /**
         * 最大执行时间
         */
        private long maxExecuteTime;
    }
}

