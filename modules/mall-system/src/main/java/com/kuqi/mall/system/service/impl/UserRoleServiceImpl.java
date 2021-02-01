package com.kuqi.mall.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuqi.mall.system.dao.UserRoleMapper;
import com.kuqi.mall.system.entity.po.UserRole;
import com.kuqi.mall.system.entity.bo.UserRoleQuery;
import com.kuqi.mall.system.service.IUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 10:23
 **/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Override
    public List<UserRole> list(UserRoleQuery userRoleQuery) {
        return this.baseMapper.selectList(this.buildQueryWrapper(userRoleQuery));
    }

    private QueryWrapper<UserRole> buildQueryWrapper(UserRoleQuery userRoleQuery) {

        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();

        Long userId;
        queryWrapper.eq(Objects.nonNull(userId = userRoleQuery.getUserId()), "user_id", userId);
        Long roleId;
        queryWrapper.eq(Objects.nonNull(roleId = userRoleQuery.getRoleId()), "role_id", roleId);
        return queryWrapper;
    }
}
