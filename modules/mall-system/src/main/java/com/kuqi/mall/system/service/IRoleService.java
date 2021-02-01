package com.kuqi.mall.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kuqi.mall.system.entity.po.Role;
import com.kuqi.mall.system.entity.bo.RoleQuery;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 20:35
 **/
public interface IRoleService extends IService<Role> {

    Role get(RoleQuery roleQuery);
}
