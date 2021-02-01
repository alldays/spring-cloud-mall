package com.kuqi.mall.web.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kuqi.mall.web.core.converter.String2LocalDateTime;
import com.kuqi.mall.web.core.databind.LocalDateTime2LongSerializer;
import com.kuqi.mall.web.core.databind.LocalDateTimeLongDeserializer;
import com.kuqi.mall.web.core.handler.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 14:49
 **/
@Configuration
@EnableWebMvc
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        for (HttpMessageConverter httpMessageConverter : converters) {
            if (httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {

                JavaTimeModule javaTimeModule = new JavaTimeModule();
                // json 时间格式转换
                javaTimeModule.addSerializer(LocalDateTime.class, LocalDateTime2LongSerializer.INSTANCE);
                javaTimeModule.addDeserializer(LocalDateTime.class, LocalDateTimeLongDeserializer.INSTANCE);
                MappingJackson2HttpMessageConverter converter = (MappingJackson2HttpMessageConverter) httpMessageConverter;
                converter.getObjectMapper().registerModule(javaTimeModule);
            }
        }
    }

    /**
     * rest接口，时间转为long
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        Jackson2ObjectMapperBuilderCustomizer customizer = jacksonObjectMapperBuilder
                -> jacksonObjectMapperBuilder
                // long时间转换
                .serializerByType(Long.class, ToStringSerializer.instance)
                .serializerByType(Long.class, ToStringSerializer.instance)
                .serializerByType(long.class, ToStringSerializer.instance)
                // 日期时间转换
                .serializerByType(LocalDateTime.class, LocalDateTime2LongSerializer.INSTANCE)
                .deserializerByType(LocalDateTime.class, LocalDateTimeLongDeserializer.INSTANCE);
        return customizer;
    }

    /**
     * 时间戳转换为LocalDateTime
     */
    @Bean
    public String2LocalDateTime long2LocalDateTime() {
        return new String2LocalDateTime();
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
