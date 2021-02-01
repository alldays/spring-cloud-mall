package com.kuqi.mall.system.config;

import com.google.common.collect.Lists;
import com.kuqi.mall.security.config.JwtTokenConfig;
import com.kuqi.mall.system.core.security.JwtTokenUserEnhancer;
import com.kuqi.mall.system.manager.IUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.List;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 22:14
 **/
@Configuration
@EnableAuthorizationServer
@AutoConfigureAfter(JwtTokenConfig.class)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenStore jwtTokenStore;
    @Autowired
    private IUserManager userManager;
    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     * jwt token 转换器
     */
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 客户端配置信息
     *
     * @param clients 客户端配置
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 从客户端获取配置信息
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }

    /**
     * 配置授权服务
     *
     * @param endpoints 授权服务端点配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

        endpoints.tokenStore(jwtTokenStore)
                .userDetailsService(userManager)
                .accessTokenConverter(jwtAccessTokenConverter)
                .authenticationManager(authenticationManager)
                .tokenEnhancer(tokenEnhancerChain());
    }

    /**
     * 授权端点访问控制
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer securityConfigurer) {

        securityConfigurer.passwordEncoder(passwordEncoder)
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * token增加链表
     */
    @Bean
    public TokenEnhancerChain tokenEnhancerChain() {

        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancerList = Lists.newArrayList(new JwtTokenUserEnhancer(), jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancerList);
        return enhancerChain;
    }
}
