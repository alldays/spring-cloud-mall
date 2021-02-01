package com.kuqi.mall.feign.core.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 15:58
 **/
@Slf4j
public class OAuth2FeignHeadersInterceptor implements RequestInterceptor {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * 需要的header参数
     */
    private static final Set<String> REQUIRED_HEADERS = Sets.newHashSet(AUTHORIZATION_HEADER, "traceId");

    @Override
    public void apply(RequestTemplate template) {

        HttpServletRequest httpServletRequest = this.getHttpServletRequest();
        if (this.shouldObtainAuthorizationFromHttpServletRequest(httpServletRequest)) {

            Map<String, String> requiredHeaderValueMap = this.findRequiredHeaderValueMap(httpServletRequest.getHeaderNames(), httpServletRequest);
            if (MapUtils.isNotEmpty(requiredHeaderValueMap)) {
                for (Map.Entry<String, String> entry : requiredHeaderValueMap.entrySet()) {
                    template.header(entry.getKey(), entry.getValue());
                }
            }
            if (this.success2ObtainAuthorizationFromHttpServletRequest(requiredHeaderValueMap)) {
                return;
            }
        }
        this.obtainAuthorizationFromAuthentication(template);
    }

    private boolean shouldObtainAuthorizationFromHttpServletRequest(HttpServletRequest httpServletRequest) {
        return Objects.nonNull(httpServletRequest);
    }

    private void obtainAuthorizationFromAuthentication(RequestTemplate template) {

        // 清理Authorization 参数
        SecurityContext securityContext;
        if (Objects.isNull(securityContext = SecurityContextHolder.getContext())) {

            log.info("no login user!");
            return;
        }
        Authentication authentication;
        Object detail;
        if (Objects.nonNull(authentication = securityContext.getAuthentication())
                && Objects.nonNull(detail = authentication.getDetails())
                && (detail instanceof OAuth2AuthenticationDetails)) {

            OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) detail;
            String tokenValue;
            if (StringUtils.isNotBlank(tokenValue = oAuth2AuthenticationDetails.getTokenValue())) {

                // 基于BearerTokenExtractor获取token值
                String authorization = OAuth2AccessToken.BEARER_TYPE + tokenValue;
                // 可以在header中设置Authorization参数的token值
                // 可以在request中设置access_token参数的token值
                template.header(AUTHORIZATION_HEADER, authorization);
            } else {
                log.info("token value is empty in detail {}!", JSON.toJSONString(oAuth2AuthenticationDetails));
            }
        }
        log.warn("no authorization exits in authentication!");
    }

    private Map<String, String> findRequiredHeaderValueMap(Enumeration<String> headerNames, HttpServletRequest request) {

        Map<String, String> headerValueMap = new HashMap<>();
        if (Objects.isNull(headerNames) || Objects.isNull(request)) {
            return headerValueMap;
        }
        while (headerNames.hasMoreElements()) {

            String header = headerNames.nextElement();
            if (this.isRequiredHeader(header)) {

                String value = request.getHeader(header);
                headerValueMap.put(header, value);
            }
        }
        return headerValueMap;
    }

    private boolean isRequiredHeader(String header) {

        for (String requiredHeader : REQUIRED_HEADERS) {
            if (requiredHeader.equalsIgnoreCase(header)) {
                return true;
            }
        }
        return false;
    }

    private boolean success2ObtainAuthorizationFromHttpServletRequest(Map<String, String> headerValueMap) {

        for (Map.Entry<String, String> entry : headerValueMap.entrySet()) {
            String header = entry.getKey();
            if (AUTHORIZATION_HEADER.equalsIgnoreCase(header)) {
                return true;
            }
        }
        return false;
    }


    private HttpServletRequest getHttpServletRequest() {

        try {
            // 这种方式获取的HttpServletRequest是线程安全的
            return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        } catch (Exception e) {
            return null;
        }
    }
}
