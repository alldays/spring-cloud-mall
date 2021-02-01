package com.kuqi.maill.common.core.validation;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 数据存在
 *
 * @Author iloveoverfly
 * @Date 2021/1/28 16:15
 **/
public abstract class AbstractValueIsExist<T, R> implements ValueValidator<T> {

    @Override
    public boolean validate(T param) {
        return isExist(param);
    }

    /**
     * 参数对应的值存在
     */
    protected boolean isExist(T param) {

        R value = this.get(param);

        // 值为null
        if (Objects.isNull(value)) {
            return false;
        }
        // 空字符串
        if (value instanceof String) {

            String strValue = (String) value;
            return StringUtils.isNotBlank(strValue);
        }
        // 集合
        if (value instanceof Collection) {

            Collection collection = (Collection) value;
            return CollectionUtils.isNotEmpty(collection);
        }
        // map
        if (value instanceof Map) {

            Map map = (Map) value;
            return MapUtils.isNotEmpty(map);
        }
        return true;
    }

    protected abstract R get(T param);
}
