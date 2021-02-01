package com.kuqi.mall.log.core.utils;

import com.kuqi.mall.log.core.entity.HttpLog;
import com.kuqi.mall.log.core.http.RequestLogWrapper;
import com.kuqi.mall.log.core.http.ResponseLogWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 15:37
 **/
@Slf4j
public class HttpLogUtil {

    public static final int MAX_LOG_LENGTH = 1024;
    public static final Set<String> IGNORE_HEADERS = new HashSet<>();


    public static String subStringIfRequired(String value) {

        if (StringUtils.isNotBlank(value) && value.length() > MAX_LOG_LENGTH) {
            return value.substring(0, MAX_LOG_LENGTH) + "......";
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取请求参数
     */
    public static String obtainHeaders(RequestLogWrapper requestLogWrapper) {

        StringBuilder headers = new StringBuilder();
        Enumeration<String> headerNames = requestLogWrapper.getHeaderNames();
        while (headerNames.hasMoreElements()) {

            String headerName = headerNames.nextElement();
            String value;
            if (!IGNORE_HEADERS.contains(headerName)
                    && StringUtils.isNotBlank(value = requestLogWrapper.getHeader(headerName))) {
                headers.append(headerName).append("=").append(value).append(";");
            }
        }
        return headers.toString();
    }

    /**
     * 获取请求参数
     */
    public static String obtainRequestParam(RequestLogWrapper requestLogWrapper) {

        String method = requestLogWrapper.getMethod();
        if (HttpMethod.GET.name().equals(method)) {
            return requestLogWrapper.getQueryString();
        }
        return requestLogWrapper.getBodyJson();
    }

    public static HttpLog obtainResponseLog(
            RequestLogWrapper requestLogWrapper, ResponseLogWrapper responseLogWrapper,
            LocalDateTime startAt, LocalDateTime finishAt, String traceId) {

        return HttpLog.builder()
                .traceId(traceId)
                .headers(obtainHeaders(requestLogWrapper))
                .methodName(requestLogWrapper.getMethod())
                .url(requestLogWrapper.getRequestURI())
                .request(obtainRequestParam(requestLogWrapper))
                .status(responseLogWrapper.getStatus())
                .startAt(startAt)
                .finishAt(finishAt)
                .duration(finishAt.toInstant(ZoneOffset.UTC).toEpochMilli() - startAt.toInstant(ZoneOffset.UTC).toEpochMilli())
                .response(new String(responseLogWrapper.getResponseData(), Charset.defaultCharset()))
                .build();
    }
}