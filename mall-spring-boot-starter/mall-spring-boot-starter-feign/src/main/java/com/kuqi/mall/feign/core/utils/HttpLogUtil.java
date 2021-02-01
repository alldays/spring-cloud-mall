package com.kuqi.mall.feign.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 20:17
 **/
@Slf4j
public class HttpLogUtil {

    /**
     * 输出日志最大长度
     */
    public static final int MAX_LOG_LENGTH = 1024;

    public static String subStringIfRequired(String value) {

        if (StringUtils.isNotBlank(value) && value.length() > MAX_LOG_LENGTH) {
            return value.substring(0, MAX_LOG_LENGTH) + "......";
        }
        return StringUtils.EMPTY;
    }
}
