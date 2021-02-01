package com.kuqi.mall.security.config;

import com.google.common.collect.Sets;
import com.kuqi.mall.security.core.error.InvalidTokenExceptionEntryPoint;
import com.kuqi.mall.security.core.token.UserContextAuthenticationConverter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Set;

/**
 * 资源服务器配置
 *
 * @Author iloveoverfly
 * @Date 2021/1/26 21:30
 **/
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AutoConfigureAfter(JwtTokenConfig.class)
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    public static final Set<String> ANT_PATTERNS = Sets.newHashSet("/v2/api-docs", "/doc.html", "/swagger-resources/**", "/", "/actuator/**", "/health", "/swagger-ui.html", "/csrf", "/webjars/**", "/swagger.json");

    /**
     * 资源属性配置
     */
    @Autowired
    private ResourceServerProperties resource;
    /**
     * jwtTokenStore
     */
    @Autowired
    private TokenStore jwtTokenStore;
    /**
     * jwt AccessToken转换器
     */
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    /**
     * 自定义免受授权路径
     */
    @Value("${resource.server.permit.patterns:''}")
    private String permitPatterns;

    /**
     * 资源http权限控制
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

        Set<String> permitAllUrls = ANT_PATTERNS;
        String[] permits;
        if (StringUtils.isNotBlank(permitPatterns)
                && ArrayUtils.isNotEmpty(permits = permitPatterns.split(","))) {
            permitAllUrls.addAll(Sets.newHashSet(permits));
        }
        http.authorizeRequests()
                .antMatchers(permitAllUrls.toArray(new String[0])).permitAll()
                .anyRequest().authenticated();
    }

    /**
     * 资源服务器自定义配置
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {

        // 设置资源id，
        // 客户端配置表oauth_client_details的 resource_ids 值包含该resourceId，授权才会通过
        resources.resourceId(resource.getResourceId());
        // 设置自定义tokenStore
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new UserContextAuthenticationConverter());
        jwtAccessTokenConverter.setAccessTokenConverter(accessTokenConverter);

        resources.tokenStore(jwtTokenStore);
        resources.stateless(false);
        resources.authenticationEntryPoint(new InvalidTokenExceptionEntryPoint());
    }
}

