package com.kuqi.mall.log.core.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 15:42
 **/
public class DateUtils {


    public static String formatDateTime(LocalDateTime date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDate(LocalDateTime date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = date.format(DateTimeFormatter.ofPattern(pattern[0].toString()));
        } else {
            formatDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return formatDate;
    }
}
