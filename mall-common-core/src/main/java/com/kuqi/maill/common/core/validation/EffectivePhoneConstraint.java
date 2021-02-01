package com.kuqi.maill.common.core.validation;

import cn.hutool.core.util.PhoneUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 电话号码校验规则
 *
 * @Author iloveoverfly
 * @Date 2020/11/30 15:21
 **/
@Slf4j
public class EffectivePhoneConstraint implements ConstraintValidator<EffectivePhone, String> {

    private boolean shouldBeNull;

    @Override
    public void initialize(EffectivePhone constraintAnnotation) {
        shouldBeNull = constraintAnnotation.shouldBeNull();
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {

        if (StringUtils.isNotBlank(phone)) {
            return PhoneUtil.isPhone(phone);
        } else {
            return shouldBeNull;
        }
    }
}
