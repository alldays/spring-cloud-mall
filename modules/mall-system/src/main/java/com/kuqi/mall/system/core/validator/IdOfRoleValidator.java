package com.kuqi.mall.system.core.validator;

import com.kuqi.maill.common.core.validation.AbstractValueIsExist;
import com.kuqi.mall.system.entity.po.Role;
import com.kuqi.mall.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色id校验
 *
 * @Author iloveoverfly
 * @Date 2021/1/28 16:21
 **/
@Service
public class IdOfRoleValidator extends AbstractValueIsExist<Long, Role> {

    @Autowired
    private IRoleService roleService;

    @Override
    protected Role get(Long id) {
        return roleService.getById(id);
    }
}
