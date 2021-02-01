package com.kuqi.mall.swagger.config;

import com.google.common.base.Predicates;
import com.kuqi.mall.swagger.core.SwaggerProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * swagger2配置，在dev，test环境有效
 *
 * @Author iloveoverfly
 * @Date 2021/1/26 16:41
 **/
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger", value = "enable", matchIfMissing = true)
@EnableConfigurationProperties(SwaggerProperties.class)
@Profile({"dev", "test"})
public class SwaggerConfig {

    private static final String AUTHENTICATION = "Authorization";

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2).
                apiInfo(new ApiInfoBuilder()
                        .title("接口文档")
                        .version("1.0.0")
                        .build())
                .useDefaultResponseMessages(false)
                .select()
                // 指定显示api的条件
                .apis(RequestHandlerSelectors.basePackage(this.swaggerProperties.getBasePackage()))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                // 使用认证令牌token 访问
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    /**
     * 认证方式配置
     * https://swagger.io/docs/specification/authentication/
     */
    private List<ApiKey> securitySchemes() {
        return newArrayList(
                new ApiKey(AUTHENTICATION, AUTHENTICATION, "header"));
    }

    /**
     * swagger2 认证的安全上下文
     */
    private List<SecurityContext> securityContexts() {

        return newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(Predicates.not(PathSelectors.regex("/login")))
                        .build()
        );
    }

    /**
     * 默认的全局鉴权策略
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference(AUTHENTICATION, authorizationScopes));
    }
}
