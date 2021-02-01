package com.kuqi.mall.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuqi.mall.system.dao.RoleMenuMapper;
import com.kuqi.mall.system.entity.po.RoleMenu;
import com.kuqi.mall.system.entity.bo.RoleMenuQuery;
import com.kuqi.mall.system.service.IRoleMenuService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 10:41
 **/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Override
    public List<RoleMenu> list(RoleMenuQuery roleMenuQuery) {
        return this.baseMapper.selectList(this.buildQueryWrapper(roleMenuQuery));
    }

    private QueryWrapper<RoleMenu> buildQueryWrapper(RoleMenuQuery roleMenuQuery) {

        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();

        Long menuId;
        queryWrapper.eq(Objects.nonNull(menuId = roleMenuQuery.getMenuId()), "menu_id", menuId);
        Long roleId;
        queryWrapper.eq(Objects.nonNull(roleId = roleMenuQuery.getRoleId()), "role_id", roleId);
        Collection<Long> roleIds;
        queryWrapper.in(CollectionUtils.isNotEmpty(roleIds = roleMenuQuery.getRoleIds()), "role_id", roleIds);
        return queryWrapper;
    }
}
