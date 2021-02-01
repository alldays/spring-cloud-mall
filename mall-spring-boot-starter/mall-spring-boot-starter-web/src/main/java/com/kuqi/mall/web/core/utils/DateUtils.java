package com.kuqi.mall.web.core.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 15:00
 **/
public class DateUtils {

    private DateUtils() {
    }

    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {

        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone =ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }
}
