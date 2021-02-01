package com.kuqi.mall.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuqi.mall.system.dao.MenuMapper;
import com.kuqi.mall.system.entity.bo.MenuQuery;
import com.kuqi.mall.system.entity.po.Menu;
import com.kuqi.mall.system.service.IMenuService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 21:42
 **/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Override
    public List<Menu> list(MenuQuery menuQuery) {
        return this.baseMapper.selectList(this.buildQueryWrapper(menuQuery));
    }

    private QueryWrapper<Menu> buildQueryWrapper(MenuQuery menuQuery) {

        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();

        String name;
        queryWrapper.eq(StringUtils.isNotBlank(name = menuQuery.getName()), "name", name);

        Integer type;
        queryWrapper.eq(Objects.nonNull(type = menuQuery.getType()), "type", type);

        Collection<Long> ids;
        queryWrapper.in(CollectionUtils.isNotEmpty(ids = menuQuery.getIds()), "id", ids);
        return queryWrapper;
    }

}
