package com.kuqi.mall.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuqi.mall.system.dao.RoleMapper;
import com.kuqi.mall.system.entity.po.Role;
import com.kuqi.mall.system.entity.bo.RoleQuery;
import com.kuqi.mall.system.service.IRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 20:35
 **/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Override
    public Role get(RoleQuery roleQuery) {
        return this.getOne(this.buildQueryWrapper(roleQuery));
    }

    private QueryWrapper<Role> buildQueryWrapper(RoleQuery roleQuery) {

        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        String name;
        queryWrapper.eq(StringUtils.isNotBlank(name = roleQuery.getName()), "name", name);
        return queryWrapper;
    }
}
