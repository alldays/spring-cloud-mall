package com.kuqi.mall.security.core.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author iloveoverfly
 * @Date 2021/1/31 10:31
 **/
@Data
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 745627789346263918L;

    private Long id;
    /**
     * 用户账号
     */
    private String userName;
    /**
     * 超级管理员
     */
    private Boolean isAdmin;
    /**
     * 用户类型（0:管理员1:普通用户）
     */
    private Integer type;
}
