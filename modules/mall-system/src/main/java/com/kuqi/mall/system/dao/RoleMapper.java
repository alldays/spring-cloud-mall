package com.kuqi.mall.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kuqi.mall.system.entity.po.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 20:34
 **/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
