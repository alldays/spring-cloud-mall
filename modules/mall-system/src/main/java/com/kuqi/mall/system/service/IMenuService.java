package com.kuqi.mall.system.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kuqi.mall.system.entity.bo.MenuQuery;
import com.kuqi.mall.system.entity.po.Menu;

import java.util.List;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 21:41
 **/
public interface IMenuService extends IService<Menu> {

    @DS("slave_1")
    List<Menu> list(MenuQuery menuQuery);
}
