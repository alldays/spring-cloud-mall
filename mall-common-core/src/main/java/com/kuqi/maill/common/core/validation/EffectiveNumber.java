package com.kuqi.maill.common.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 有效的数值
 *
 * @Author iloveoverfly
 * @Date 2020/11/27 10:03
 **/
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(EffectiveNumber.List.class)
@Documented
@Constraint(
        validatedBy = {EffectiveNumberConstraint.class}
)
public @interface EffectiveNumber {

    String message() default "{javax.validation.constraints.EffectiveEnum.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 比较大小的方法名称
     */
    String compareMethodName();

    Class<?> parameterType();

    boolean shouldBeNull() default false;

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        EffectiveNumber[] value();
    }
}
