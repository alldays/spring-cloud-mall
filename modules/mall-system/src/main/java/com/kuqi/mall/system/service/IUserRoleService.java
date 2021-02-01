package com.kuqi.mall.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kuqi.mall.system.entity.po.UserRole;
import com.kuqi.mall.system.entity.bo.UserRoleQuery;

import java.util.List;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 10:22
 **/
public interface IUserRoleService extends IService<UserRole> {

    List<UserRole> list(UserRoleQuery userRoleQuery);
}
