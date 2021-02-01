package com.kuqi.maill.common.core.validation;

import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @Author iloveoverfly
 * @Date 2020/12/26 23:23
 **/
public class NumberValidator {
    
    /**
     * 大于等于0
     */
    public static boolean isGreaterOrEqualZero(Number number) {
        return Objects.nonNull(number) && NumberUtil.isGreaterOrEqual(BigDecimal.valueOf(number.longValue()), BigDecimal.ZERO);
    }
}
