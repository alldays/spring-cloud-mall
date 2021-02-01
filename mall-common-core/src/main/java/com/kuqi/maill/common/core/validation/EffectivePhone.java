package com.kuqi.maill.common.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 有效的电话号码
 *
 * @Author iloveoverfly
 * @Date 2020/11/30 15:20
 **/
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(EffectivePhone.List.class)
@Documented
@Constraint(
        validatedBy = {EffectivePhoneConstraint.class}
)
public @interface EffectivePhone {

    String message() default "{javax.validation.constraints.EffectivePhone.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean shouldBeNull() default false;

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        EffectivePhone[] value();
    }
}
