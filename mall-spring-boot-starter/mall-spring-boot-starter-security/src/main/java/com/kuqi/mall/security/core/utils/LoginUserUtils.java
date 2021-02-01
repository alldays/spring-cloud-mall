package com.kuqi.mall.security.core.utils;

import com.kuqi.mall.security.core.entity.LoginUser;
import com.kuqi.mall.security.core.token.UserContextAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Objects;

/**
 * @Author iloveoverfly
 * @Date 2021/1/31 19:40
 **/
public class LoginUserUtils {
    private LoginUserUtils() {
    }

    /**
     * 获取登录用户
     */
    public static LoginUser getLoginUser() {

        OAuth2Authentication auth2Authentication = getAuthentication();
        Authentication userAuthentication;
        if (Objects.nonNull(auth2Authentication)
                && (Objects.nonNull(userAuthentication = auth2Authentication.getUserAuthentication()))
                && (userAuthentication instanceof UserContextAuthenticationToken)) {

            UserContextAuthenticationToken userContextAuthenticationToken = (UserContextAuthenticationToken) userAuthentication;
            return userContextAuthenticationToken.getLoginUser();
        }
        return null;
    }

    private static OAuth2Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Objects.nonNull(authentication))
                && (authentication instanceof OAuth2Authentication) ? (OAuth2Authentication) authentication : null;
    }
}
