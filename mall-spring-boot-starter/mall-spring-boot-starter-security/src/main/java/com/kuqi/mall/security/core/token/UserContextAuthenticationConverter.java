package com.kuqi.mall.security.core.token;

import com.kuqi.mall.security.core.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @Author iloveoverfly
 * @Date 2021/1/31 10:40
 **/
public class UserContextAuthenticationConverter extends DefaultUserAuthenticationConverter {

    public static final String IS_ADMIN = "isAdmin";
    public static final String TYPE = "type";

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {

        LoginUser loginUser = new LoginUser();
        loginUser.setUserName((String) map.get(UserAuthenticationConverter.USERNAME));
        loginUser.setIsAdmin((Boolean) map.get(IS_ADMIN));
        loginUser.setType((Integer) map.get(TYPE));
        Collection<GrantedAuthority> authorities = this.getAuthorities(map);
        return new UserContextAuthenticationToken(loginUser.getUserName(), null, authorities, loginUser);
    }

    private Collection<GrantedAuthority> getAuthorities(Map<String, ?> map) {

        Object authorities = map.get(UserAuthenticationConverter.AUTHORITIES);
        if (Objects.isNull(authorities)) {
            authorities = Collections.EMPTY_LIST;
        }
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }
}
