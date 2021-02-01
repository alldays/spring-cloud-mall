package com.kuqi.mall.system.manager;

import com.kuqi.mall.system.entity.dto.SaveMenuDto;
import com.kuqi.mall.system.entity.dto.UpdateMenuDto;
import com.kuqi.mall.system.entity.query.MenuListQuery;
import com.kuqi.mall.system.entity.vo.MenuVo;

import java.util.List;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 21:43
 **/
public interface IMenuManager {

    List<MenuVo> list(MenuListQuery menuListQuery);

    MenuVo save(SaveMenuDto saveMenuDto);

    MenuVo update(UpdateMenuDto updateMenuDto);

    boolean delete(Long id);
}
