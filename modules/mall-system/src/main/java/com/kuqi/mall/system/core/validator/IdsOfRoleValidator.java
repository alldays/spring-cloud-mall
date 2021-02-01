package com.kuqi.mall.system.core.validator;

import com.kuqi.maill.common.core.validation.AbstractValuesAreExist;
import com.kuqi.mall.system.entity.po.Role;
import com.kuqi.mall.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 13:42
 **/
@Service
public class IdsOfRoleValidator extends AbstractValuesAreExist<Collection<Long>, List<Role>> {

    @Autowired
    private IRoleService roleService;

    @Override
    protected List<Role> list(Collection<Long> ids) {
        return roleService.listByIds(ids);
    }
}
