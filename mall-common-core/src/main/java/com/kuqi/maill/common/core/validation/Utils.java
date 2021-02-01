package com.kuqi.maill.common.core.validation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Author iloveoverfly
 * @Date 2020/12/23 17:26
 **/
@Slf4j
public class Utils<T> {
    private Utils() {
    }

    public static <T> boolean valid(T value, Class<? extends Enum> enumClazz, String getterMethodName) {

        if (Objects.isNull(value) || Objects.isNull(enumClazz) || StringUtils.isBlank(getterMethodName)) {
            return false;
        }
        try {
            Method getterMethod = enumClazz.getMethod(getterMethodName, value.getClass());
            return Objects.nonNull(getterMethod.invoke(null, value));
        } catch (Exception e) {
            log.warn("validate enum error!enumClazz {},getterMethodName {},value {}", enumClazz.getName(), getterMethodName, value);
        }
        return false;
    }

    public static <T> boolean exist(T value, Class<? extends Enum> enumClazz, String getterMethodName) {

        if (Objects.isNull(value) || Objects.isNull(enumClazz) || StringUtils.isBlank(getterMethodName)) {
            return false;
        }
        try {
            Method getterMethod = enumClazz.getMethod(getterMethodName, value.getClass());
            boolean flag = (boolean)getterMethod.invoke(null, value);
            return flag;
        } catch (Exception e) {
            log.warn("validate enum error!enumClazz {},getterMethodName {},value {}", enumClazz.getName(), getterMethodName, value);
        }
        return false;
    }
}
