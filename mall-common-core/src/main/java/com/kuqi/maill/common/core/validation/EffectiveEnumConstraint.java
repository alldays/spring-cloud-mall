package com.kuqi.maill.common.core.validation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * 数值枚举类校验
 * @Author iloveoverfly
 * @Date 2020/11/27 10:10
 **/
@Slf4j
public class EffectiveEnumConstraint implements ConstraintValidator<EffectiveEnum, Object> {

    private Class<? extends Enum> enumClazz;
    private String getterMethodName;
    private boolean shouldBeNull;

    @Override
    public void initialize(EffectiveEnum constraintAnnotation) {
        this.enumClazz = constraintAnnotation.enumClass();
        this.getterMethodName = constraintAnnotation.getterMethodName();
        this.shouldBeNull = constraintAnnotation.shouldBeNull();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        if (Objects.nonNull(value)) {
            return Utils.valid(value, enumClazz, getterMethodName);
        } else {
            return shouldBeNull;
        }
    }
}
