package com.kuqi.mall.system.entity.vo;

import com.kuqi.mall.system.entity.enums.UserSex;
import com.kuqi.mall.system.entity.enums.UserStatus;
import com.kuqi.mall.system.entity.enums.UserType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 22:18
 **/
@Data
@ApiModel("用户vo")
public class UserVo implements UserDetails, Serializable {

    private static final long serialVersionUID = 7701702463245961855L;
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户账号")
    private String userName;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("超级管理员")
    private Boolean isAdmin;

    /**
     * 用户类型（0:管理员1:普通用户）
     * {@link UserType}
     */
    @ApiModelProperty("用户类型（0:管理员1:普通用户）")
    private Integer type;

    /**
     * 帐号状态（0正常 1停用）
     * {@link UserStatus}
     */
    @ApiModelProperty("帐号状态（0正常 1停用）")
    private Integer status;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("手机号码")
    private String phone;

    /**
     * 性别
     * {@link UserSex}
     */
    @ApiModelProperty("用户性别（0男 1女 2未知）")
    private Integer sex;

    @ApiModelProperty("头像地址")
    private String avatar;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户角色")
    private List<RoleVo> roleList;

    @ApiModelProperty("用户菜单")
    private List<MenuVo> menuList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return menuList;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 是否启用该账号
        return UserStatus.normal.getValue().equals(this.status);
    }
}
