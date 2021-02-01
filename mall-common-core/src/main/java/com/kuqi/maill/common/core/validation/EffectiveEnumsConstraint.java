package com.kuqi.maill.common.core.validation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * 枚举列表验证
 *
 * @Author iloveoverfly
 * @Date 2020/11/27 10:10
 **/
@Slf4j
public class EffectiveEnumsConstraint implements ConstraintValidator<EffectiveEnums, List<Integer>> {

    private Class<? extends Enum> enumClazz;
    private String getterMethodName;
    private boolean shouldBeNull;

    @Override
    public void initialize(EffectiveEnums constraintAnnotation) {
        this.enumClazz = constraintAnnotation.enumClass();
        this.getterMethodName = constraintAnnotation.getterMethodName();
        this.shouldBeNull = constraintAnnotation.shouldBeNull();
    }

    @Override
    public boolean isValid(List<Integer> values, ConstraintValidatorContext constraintValidatorContext) {

        if (CollectionUtils.isNotEmpty(values)) {
            return values.stream()
                    .allMatch(value -> Utils.valid(value, enumClazz, getterMethodName));
        } else {
            return shouldBeNull;
        }
    }
}
