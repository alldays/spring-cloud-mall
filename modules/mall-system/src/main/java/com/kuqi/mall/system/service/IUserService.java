package com.kuqi.mall.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kuqi.mall.system.entity.bo.UserQuery;
import com.kuqi.mall.system.entity.po.User;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 10:06
 **/
public interface IUserService extends IService<User> {

    User get(UserQuery userQuery);
}
