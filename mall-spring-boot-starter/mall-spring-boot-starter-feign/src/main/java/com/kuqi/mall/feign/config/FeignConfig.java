package com.kuqi.mall.feign.config;

import com.kuqi.mall.feign.core.interceptor.OAuth2FeignHeadersInterceptor;
import com.kuqi.mall.feign.core.logger.Slf4jFeignLoggerFactory;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * feign 配置
 * @Author iloveoverfly
 * @Date 2021/1/27 16:04
 **/
@Configuration
public class FeignConfig {

    /**
     * feign 传递token
     */
    @Bean
    public RequestInterceptor oAuth2FeignHeadersInterceptor() {
        return new OAuth2FeignHeadersInterceptor();
    }

    /**
     * 打印完整信息，包括请求和响应的所有信息
     */
    @Bean
    public Logger.Level feignLevel() {
        return Logger.Level.FULL;
    }

    /**
     * 自定义info级别的Feign 日志输出
     */
    @Bean
    public FeignLoggerFactory slf4jFeignLoggerFactory() {
        return new Slf4jFeignLoggerFactory();
    }
}
