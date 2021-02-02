package com.kuqi.mall.swagger.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author iloveoverfly
 * @Date 2021/1/26 22:40
 **/
@Data
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    private String title;
    private String description;
    private String version;
    private String basePackage;
}
