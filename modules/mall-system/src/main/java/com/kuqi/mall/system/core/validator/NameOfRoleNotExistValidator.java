package com.kuqi.mall.system.core.validator;

import com.kuqi.maill.common.core.validation.AbstractValueNotExist;
import com.kuqi.mall.system.entity.po.Role;
import com.kuqi.mall.system.entity.bo.RoleQuery;
import com.kuqi.mall.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色名称存在校验
 *
 * @Author iloveoverfly
 * @Date 2021/1/28 20:39
 **/
@Service
public class NameOfRoleNotExistValidator extends AbstractValueNotExist<String, Role> {

    @Autowired
    private IRoleService roleService;

    @Override
    protected Role get(String name) {
        return roleService.get(RoleQuery.builder().name(name).build());
    }
}
