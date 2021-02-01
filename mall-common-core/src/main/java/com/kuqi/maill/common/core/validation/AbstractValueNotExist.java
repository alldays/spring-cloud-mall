package com.kuqi.maill.common.core.validation;

/**
 * 数据不存在
 *
 * @Author iloveoverfly
 * @Date 2021/1/28 16:15
 **/
public abstract class AbstractValueNotExist<T, R> extends AbstractValueIsExist<T, R> {

    @Override
    public boolean validate(T param) {
        return !super.isExist(param);
    }
}
