package com.kuqi.mall.feign.core.logger;

import feign.Logger;
import org.springframework.cloud.openfeign.FeignLoggerFactory;

import java.util.Objects;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 20:12
 **/
public class Slf4jFeignLoggerFactory implements FeignLoggerFactory {

    private Logger logger;

    public Slf4jFeignLoggerFactory(Logger logger) {
        this.logger = logger;
    }

    public Slf4jFeignLoggerFactory() {
    }

    @Override
    public Logger create(Class<?> type) {
        return Objects.nonNull(this.logger) ? this.logger : new Slf4jFeignLogger(type);
    }
}

