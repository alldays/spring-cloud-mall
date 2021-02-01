package com.kuqi.maill.common.core.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 20:24
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OptimisticRetry {

    /**
     * 最大重试次数
     */
    int value() default 20;

    /**
     * 最大执行时间
     */
    int maxExecuteTime() default 2 * 60 * 1000;
}