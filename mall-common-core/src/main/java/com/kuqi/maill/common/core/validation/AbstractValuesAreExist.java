package com.kuqi.maill.common.core.validation;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 13:46
 **/
public abstract class AbstractValuesAreExist<T extends Collection, R extends Collection> implements ValueValidator<T> {
    @Override
    public boolean validate(T param) {

        if (CollectionUtils.isEmpty(param)) {
            return false;
        }
        R collection;
        if (CollectionUtils.isEmpty(collection = this.list(param))) {
            return false;
        }
        return param.size() == collection.size();
    }

    protected abstract R list(T param);

}
