package com.kuqi.mall.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kuqi.mall.system.entity.po.RoleMenu;
import com.kuqi.mall.system.entity.bo.RoleMenuQuery;

import java.util.List;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 10:40
 **/
public interface IRoleMenuService extends IService<RoleMenu> {

    List<RoleMenu> list(RoleMenuQuery roleMenuQuery);
}
