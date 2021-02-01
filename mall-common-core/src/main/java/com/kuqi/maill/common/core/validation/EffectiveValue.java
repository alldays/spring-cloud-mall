package com.kuqi.maill.common.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 有效的数据id校验
 *
 * @Author iloveoverfly
 * @Date 2020/11/27 13:55
 **/
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(EffectiveValue.List.class)
@Documented
@Constraint(
        validatedBy = {EffectiveValueConstraint.class}
)
public @interface EffectiveValue {

    String message() default "{javax.validation.constraints.EffectiveValue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * spring 容器的中服务bean
     */
    Class<? extends ValueValidator> serviceBean();

    /**
     * 被校验值允许为空
     */
    boolean shouldBeNull() default false;

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        EffectiveValue[] value();
    }

}
