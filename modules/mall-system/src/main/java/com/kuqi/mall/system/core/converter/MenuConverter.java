package com.kuqi.mall.system.core.converter;

import com.kuqi.mall.system.entity.bo.MenuQuery;
import com.kuqi.mall.system.entity.dto.SaveMenuDto;
import com.kuqi.mall.system.entity.dto.UpdateMenuDto;
import com.kuqi.mall.system.entity.po.Menu;
import com.kuqi.mall.system.entity.query.MenuListQuery;
import com.kuqi.mall.system.entity.vo.MenuVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 11:05
 **/
@Mapper(componentModel = "spring")
public interface MenuConverter {

    MenuConverter INSTANCE = Mappers.getMapper(MenuConverter.class);

    MenuQuery toMenuQuery(MenuListQuery menuListQuery);

    Menu fromSaveMenuDto(SaveMenuDto saveMenuDto);

    Menu fromUpdateMenuDto(UpdateMenuDto updateMenuDto);

    MenuVo toMenuVo(Menu menu);

    List<MenuVo> toMenuVo(List<Menu> menuList);
}
