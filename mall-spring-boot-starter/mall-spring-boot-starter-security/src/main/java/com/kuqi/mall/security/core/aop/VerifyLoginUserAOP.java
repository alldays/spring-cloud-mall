package com.kuqi.mall.security.core.aop;

import com.kuqi.mall.security.core.entity.LoginUser;
import com.kuqi.mall.security.core.utils.LoginUserUtils;
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
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @Author iloveoverfly
 * @Date 2021/1/31 22:01
 **/
@Aspect
@Component
@Order(10000)
@Slf4j
public class VerifyLoginUserAOP implements InitializingBean {

    @Pointcut("@annotation(VerifyLoginUser)||@within(VerifyLoginUser)")
    public void verifyLoginUserPointcut() {
    }

    @Around("verifyLoginUserPointcut()")
    public Object doVerifyLoginUser(ProceedingJoinPoint joinPoint) throws Throwable {

        LoginUser loginUser;
        if (Objects.isNull(loginUser = LoginUserUtils.getLoginUser())) {
            throw new IllegalArgumentException(VerifyLoginUser.NOT_LOGIN);
        }
        Signature signature =joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        // 代理目标对象Class
        Class targetClazz = joinPoint.getTarget().getClass();
        // 类上的用户验证注解
        Optional<VerifyLoginUser> clazzVerifyLoginUser = getVerifyLoginUser(targetClazz.getAnnotations());
        // 方法上的用户验证注解
        Method method = targetClazz.getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        Optional<VerifyLoginUser> methodVerifyLoginUser = getVerifyLoginUser(method.getAnnotations());

        // 首先判断方法上的注解
        if (methodVerifyLoginUser.isPresent()) {
            verifyLoginUser(methodVerifyLoginUser.get(), loginUser.getType());
        } else {
            clazzVerifyLoginUser.ifPresent(verifyLoginUser -> verifyLoginUser(verifyLoginUser, loginUser.getType()));
        }
        return joinPoint.proceed();
    }

    private void verifyLoginUser(VerifyLoginUser verifyLoginUser, Integer type) {

        Set<Integer> types = new HashSet<>();
        for (int t : verifyLoginUser.type()) {
            types.add(t);
        }
        if (!types.contains(type)) {
            throw new IllegalArgumentException(verifyLoginUser.errorMsg());
        }
    }

    private Optional<VerifyLoginUser> getVerifyLoginUser(Annotation[] annotations) {

        if (ArrayUtils.isEmpty(annotations)) {
            return Optional.empty();
        }
        for (Annotation anno : annotations) {
            if (anno.annotationType().getName().equals(VerifyLoginUser.class.getName())) {
                return Optional.of((VerifyLoginUser) anno);
            }
        }
        return Optional.empty();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("init verify login user!");
    }
}
