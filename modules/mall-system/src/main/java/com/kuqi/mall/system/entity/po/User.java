package com.kuqi.mall.system.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kuqi.mall.mybatis.core.entity.BaseEntity;
import com.kuqi.mall.mybatis.core.handler.EncryptorsHandler;
import com.kuqi.mall.system.entity.enums.UserSex;
import com.kuqi.mall.system.entity.enums.UserStatus;
import com.kuqi.mall.system.entity.enums.UserType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 22:03
 **/
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "user", autoResultMap = true)
@Data
public class User extends BaseEntity {
    private static final long serialVersionUID = -3061035553231303994L;

    /**
     * 用户账号
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 用户类型（0:管理员1:普通用户）
     * {@link UserType}
     */
    @TableField("type")
    private Integer type;

    /**
     * 帐号状态（0正常 1停用）
     * {@link UserStatus}
     */
    @TableField("status")
    private Integer status;

    /**
     * 用户邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号码
     */
    @TableField(value = "phone", typeHandler = EncryptorsHandler.class)
    private String phone;

    /**
     * 性别
     * {@link UserSex}
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 头像地址
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 超级管理员
     */
    @TableField("is_admin")
    private Boolean isAdmin;
}
