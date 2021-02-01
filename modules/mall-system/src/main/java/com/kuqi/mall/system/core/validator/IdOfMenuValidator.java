package com.kuqi.mall.system.core.validator;

import com.kuqi.maill.common.core.validation.AbstractValueIsExist;
import com.kuqi.mall.system.entity.po.Menu;
import com.kuqi.mall.system.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜单id校验
 *
 * @Author iloveoverfly
 * @Date 2021/1/28 16:21
 **/
@Service
public class IdOfMenuValidator extends AbstractValueIsExist<Long, Menu> {

    @Autowired
    private IMenuService menuService;

    @Override
    protected Menu get(Long id) {
        return menuService.getById(id);
    }
}
