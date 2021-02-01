package com.kuqi.mall.system.core.security;

import com.kuqi.mall.security.core.token.UserContextAuthenticationConverter;
import com.kuqi.mall.system.entity.vo.UserVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author iloveoverfly
 * @Date 2021/1/31 10:44
 **/
public class JwtTokenUserEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

        Map<String, Object> additionalInformation = new LinkedHashMap<>(oAuth2AccessToken.getAdditionalInformation());
        Authentication authentication;
        Object principal;
        if (Objects.nonNull(authentication = oAuth2Authentication.getUserAuthentication())
                && Objects.nonNull(principal = authentication.getPrincipal())
                && principal instanceof UserVo) {

            UserVo userVo = (UserVo) principal;
            // 设置附加信息
            Map<String, Object> info;
            if (MapUtils.isNotEmpty(info = this.getAdditionalInformationByUser(userVo))) {
                additionalInformation.putAll(info);
            }
        }
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInformation);
        return oAuth2AccessToken;
    }

    private Map<String, Object> getAdditionalInformationByUser(UserVo userVo) {

        Map<String, Object> builder = new HashMap<>();
        // 用户权限列表
        Collection<? extends GrantedAuthority> grantedAuthorities;
        List<String> authorities;
        if (CollectionUtils.isNotEmpty(grantedAuthorities = userVo.getAuthorities())
                && CollectionUtils.isNotEmpty(authorities = grantedAuthorities.stream().map(GrantedAuthority::getAuthority).filter(Objects::nonNull).collect(Collectors.toList()))) {

            builder.put(UserAuthenticationConverter.AUTHORITIES, authorities);
        }
        String userName;
        if (StringUtils.isNotBlank(userName = userVo.getUserName())) {
            builder.put(UserAuthenticationConverter.USERNAME, userName);
        }
        Boolean isAdmin;
        if (Objects.nonNull(isAdmin = userVo.getIsAdmin())) {
            builder.put(UserContextAuthenticationConverter.IS_ADMIN, isAdmin);
        }
        Integer type;
        if (Objects.nonNull(type = userVo.getType())) {
            builder.put(UserContextAuthenticationConverter.TYPE, type);
        }
        return builder;
    }
}
