package com.kuqi.mall.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kuqi.mall.system.entity.po.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 10:40
 **/
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
}
