package com.kuqi.mall.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kuqi.mall.system.entity.po.Menu;
import com.kuqi.mall.system.entity.vo.MenuVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 21:40
 **/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
}
