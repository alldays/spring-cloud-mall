package com.kuqi.mall.security.core.aop;

/**
 * 验证用户登录
 *
 * @Author iloveoverfly
 * @Date 2021/1/31 22:00
 **/
public @interface VerifyLoginUser {

    String NOT_LOGIN = "用户未登录";

    String INVALID_ADMIN_TYPE = "用户不是管理员";

    String INVALID_CONSUMER_TYPE = "用户不是客户";

    int IS_ADMIN = 0;
    int IS_CONSUMER = 1;

    int[] type() default {IS_ADMIN, IS_CONSUMER};

    String errorMsg() default NOT_LOGIN;
}
