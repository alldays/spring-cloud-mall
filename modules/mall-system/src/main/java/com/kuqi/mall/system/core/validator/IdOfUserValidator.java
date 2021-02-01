package com.kuqi.mall.system.core.validator;

import com.kuqi.maill.common.core.validation.AbstractValueIsExist;
import com.kuqi.mall.system.entity.po.User;
import com.kuqi.mall.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 13:40
 **/
@Service
public class IdOfUserValidator extends AbstractValueIsExist<Long, User> {

    @Autowired
    private IUserService userService;

    @Override
    protected User get(Long id) {
        return userService.getById(id);
    }
}
