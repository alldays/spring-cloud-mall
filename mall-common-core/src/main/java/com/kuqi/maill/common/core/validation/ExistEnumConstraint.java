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
public class ExistEnumConstraint implements ConstraintValidator<ExistEnum, Object> {

    private Class<? extends Enum> enumClazz;
    private String getterMethodName;

    @Override
    public void initialize(ExistEnum constraintAnnotation) {
        this.enumClazz = constraintAnnotation.enumClass();
        this.getterMethodName = constraintAnnotation.getterMethodName();

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        if (Objects.nonNull(value)) {
            return Utils.exist(value, enumClazz, getterMethodName);
        }

        return false;
    }
}
