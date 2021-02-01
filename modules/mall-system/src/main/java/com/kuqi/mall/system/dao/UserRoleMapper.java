package com.kuqi.mall.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kuqi.mall.system.entity.po.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 10:22
 **/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
