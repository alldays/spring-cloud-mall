package com.kuqi.mall.feign.core;

import com.kuqi.mall.feign.core.entity.HttpLog;

import java.util.Objects;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 20:15
 **/
public class HttpLogHolder {

    private static final ThreadLocal<HttpLog> HTTP_LOG_HOLDER = new ThreadLocal<>();

    public static void set(HttpLog httpLog) {

        if (Objects.nonNull(httpLog)) {
            HTTP_LOG_HOLDER.set(httpLog);
        }
    }

    public static HttpLog getAndRelease() {

        HttpLog httpLog = HTTP_LOG_HOLDER.get();
        HTTP_LOG_HOLDER.remove();
        return httpLog;
    }
}
