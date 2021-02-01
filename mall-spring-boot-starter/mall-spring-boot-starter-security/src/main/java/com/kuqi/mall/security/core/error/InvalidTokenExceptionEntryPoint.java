package com.kuqi.mall.security.core.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuqi.mall.security.core.error.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证异常处理
 *
 * @Author iloveoverfly
 * @Date 2021/1/31 22:31
 **/
@Slf4j
public class InvalidTokenExceptionEntryPoint extends OAuth2AuthenticationEntryPoint {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String AUTH_TOKEN_INVALID = "无效的token";
    private static final String AUTH_TOKEN_EXPIRED = "token已过期";
    private static final String FORCE_LOGIN_AGAIN_MSG = "请重新登录";
    private static final String INVALID_TOKEN_ERROR_MSG = "Cannot convert access token to JSON";
    private static final String EXPIRED_TOKEN_EROOR_MSG = "Access token expired";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        log.warn("fail to authenticate!", e);
        Response<String> errorResponse;
        if (this.isInvalidToken(e)) {
            errorResponse = Response.error(AUTH_TOKEN_INVALID, HttpStatus.BAD_REQUEST.value());
        } else if (this.isExpiredToken(e)) {
            errorResponse = Response.error(AUTH_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED.value());
        } else {
            errorResponse = Response.error(FORCE_LOGIN_AGAIN_MSG, HttpStatus.UNAUTHORIZED.value());
        }
        writerError(errorResponse, response);
    }

    private boolean isInvalidToken(AuthenticationException e) {

        String errorMsg;
        return e instanceof InsufficientAuthenticationException &&
                StringUtils.isNotBlank(errorMsg = e.getMessage())
                && INVALID_TOKEN_ERROR_MSG.equalsIgnoreCase(errorMsg);
    }

    private boolean isExpiredToken(AuthenticationException e) {

        String errorMsg;
        return e instanceof InsufficientAuthenticationException &&
                StringUtils.isNotBlank(errorMsg = e.getMessage())
                && -1 != errorMsg.indexOf(EXPIRED_TOKEN_EROOR_MSG);
    }

    private void writerError(Response<String> errorResponse, HttpServletResponse response) throws IOException {

        response.setContentType("application/json;charset=utf-8");
        response.setStatus(200);
        OBJECT_MAPPER.writeValue(response.getOutputStream(), errorResponse);
    }
}
