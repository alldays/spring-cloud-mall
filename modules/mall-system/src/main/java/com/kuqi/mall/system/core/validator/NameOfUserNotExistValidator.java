package com.kuqi.mall.system.core.validator;

import com.kuqi.maill.common.core.validation.AbstractValueNotExist;
import com.kuqi.mall.system.entity.dto.SaveUserDto;
import com.kuqi.mall.system.entity.po.User;
import com.kuqi.mall.system.entity.bo.UserQuery;
import com.kuqi.mall.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 10:59
 **/
@Service
public class NameOfUserNotExistValidator extends AbstractValueNotExist<SaveUserDto, User> {

    @Autowired
    private IUserService userService;

    @Override
    protected User get(SaveUserDto saveUserDto) {

        return this.userService.get(UserQuery.builder().userName(saveUserDto.getUserName())
                .type(saveUserDto.getType()).build());
    }
}
