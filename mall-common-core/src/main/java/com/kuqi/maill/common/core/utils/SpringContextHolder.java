package com.kuqi.maill.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 17:00
 **/
@Slf4j
@Component
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    /**
     *
     */
    private volatile static ApplicationContext applicationContext;

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {

        synchronized (SpringContextHolder.class) {
            if (Objects.nonNull(SpringContextHolder.applicationContext)) {
                return;
            }
            synchronized (SpringContextHolder.class) {
                SpringContextHolder.applicationContext = applicationContext;
            }
        }
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(String name) {

        if (StringUtils.isBlank(name)) {
            return null;
        }
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {

        if (StringUtils.isBlank(name)) {
            return null;
        }
        return (T) applicationContext.getBean(name, requiredType);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {

        if (null == requiredType) {
            return null;
        }
        return applicationContext.getBean(requiredType);
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     */
    @Override
    public void destroy() throws Exception {
        applicationContext = null;
    }
}

