package com.kuqi.mall.log.config;

import com.kuqi.mall.log.core.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 15:53
 **/
@Configuration
public class LogAutoConfig {

    @Bean
    public LogFilter logFilter() {
        return new LogFilter();
    }

    @Bean
    public FilterRegistrationBean registerLogFilterBean() {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(logFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("logFilter");
        filterRegistrationBean.setOrder(-10000);
        return filterRegistrationBean;
    }
}
