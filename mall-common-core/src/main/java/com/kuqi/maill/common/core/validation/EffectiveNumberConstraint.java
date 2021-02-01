package com.kuqi.maill.common.core.validation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 数值校验
 *
 * @Author iloveoverfly
 * @Date 2020/11/27 10:10
 **/
@Slf4j
public class EffectiveNumberConstraint implements ConstraintValidator<EffectiveNumber, Number> {

    /**
     * 比较数值大小的方法
     */
    private String compareMethodName;
    private Class<?> parameterType;
    private boolean shouldBeNull;

    @Override
    public void initialize(EffectiveNumber constraintAnnotation) {
        this.compareMethodName = constraintAnnotation.compareMethodName();
        this.parameterType = constraintAnnotation.parameterType();
        this.shouldBeNull = constraintAnnotation.shouldBeNull();
    }

    @Override
    public boolean isValid(Number number, ConstraintValidatorContext constraintValidatorContext) {

        if (StringUtils.isNotBlank(compareMethodName) && Objects.nonNull(number)) {

            try {
                Method compareMethod = NumberValidator.class.getMethod(compareMethodName, parameterType);
                return (boolean) compareMethod.invoke(null, number);
            } catch (Exception e) {

                log.warn("compare number {} error!compareMethodName {}", number, compareMethodName);
                return false;
            }
        } else {
            return shouldBeNull;
        }
    }
}
