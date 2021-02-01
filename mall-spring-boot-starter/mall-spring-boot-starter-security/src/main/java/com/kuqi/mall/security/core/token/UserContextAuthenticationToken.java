package com.kuqi.mall.security.core.token;

import com.kuqi.mall.security.core.entity.LoginUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author iloveoverfly
 * @Date 2021/1/31 10:35
 **/
public class UserContextAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final LoginUser loginUser;

    public UserContextAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, LoginUser loginUser) {
        super(principal, credentials, authorities);
        this.loginUser = loginUser;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }
}
