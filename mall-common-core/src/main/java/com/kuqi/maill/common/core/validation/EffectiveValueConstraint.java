package com.kuqi.maill.common.core.validation;

import com.kuqi.maill.common.core.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * 值有效性校验
 *
 * @Author iloveoverfly
 * @Date 2020/11/27 13:58
 **/
@Slf4j
public class EffectiveValueConstraint implements ConstraintValidator<EffectiveValue, Object> {

    /**
     * 值有效性校验
     */
    private Class<? extends ValueValidator> serviceBean;

    /**
     * 被校验值允许为空
     */
    private boolean shouldBeNull;

    @Override
    public void initialize(EffectiveValue constraintAnnotation) {
        this.serviceBean = constraintAnnotation.serviceBean();
        this.shouldBeNull = constraintAnnotation.shouldBeNull();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        if (Objects.nonNull(value)) {

            ValueValidator valueValidator;
            if (Objects.isNull(valueValidator = SpringContextHolder.getBean(serviceBean))) {
                return false;
            }
            return valueValidator.validate(value);
        } else {
            return shouldBeNull;
        }
    }
}
