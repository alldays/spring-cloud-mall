package com.kuqi.maill.common.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 枚举类key值存在
 * @Author iloveoverfly
 * @Date 2020/11/27 10:03
 **/
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ExistEnum.List.class)
@Documented
@Constraint(
        validatedBy = {ExistEnumConstraint.class}
)
public @interface ExistEnum {

    String message() default "{javax.validation.constraints.ExistEnum.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 枚举类Class
     */
    Class<? extends Enum> enumClass();

    /**
     * 获取枚举类的方法名
     */
    String getterMethodName();

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        ExistEnum[] value();
    }
}
