package com.kuqi.mall.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuqi.mall.system.dao.UserMapper;
import com.kuqi.mall.system.entity.bo.UserQuery;
import com.kuqi.mall.system.entity.po.User;
import com.kuqi.mall.system.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 10:06
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Override
    public User get(UserQuery userQuery) {
        return this.getOne(this.buildQueryWrapper(userQuery));
    }


    private QueryWrapper<User> buildQueryWrapper(UserQuery userQuery) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        String userName;
        queryWrapper.eq(StringUtils.isNotBlank(userName = userQuery.getUserName()), "user_name", userName);
        Integer type;
        queryWrapper.eq(Objects.nonNull(type = userQuery.getType()), "type", type);
        return queryWrapper;
    }
}
