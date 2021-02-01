package com.kuqi.mall.system.core.validator;

import com.kuqi.maill.common.core.validation.AbstractValuesAreExist;
import com.kuqi.mall.system.entity.po.Menu;
import com.kuqi.mall.system.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 13:42
 **/
@Service
public class IdsOfMenuValidator extends AbstractValuesAreExist<Collection<Long>, List<Menu>> {

    @Autowired
    private IMenuService menuService;

    @Override
    protected List<Menu> list(Collection<Long> ids) {
        return menuService.listByIds(ids);
    }
}
