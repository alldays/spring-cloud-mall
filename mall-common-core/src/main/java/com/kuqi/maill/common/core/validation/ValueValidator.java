package com.kuqi.maill.common.core.validation;

/**
 * 值有效性校验
 *
 * @Author iloveoverfly
 * @Date 2020/11/27 14:02
 **/
public interface ValueValidator<T> {

    boolean validate(T value);
}
