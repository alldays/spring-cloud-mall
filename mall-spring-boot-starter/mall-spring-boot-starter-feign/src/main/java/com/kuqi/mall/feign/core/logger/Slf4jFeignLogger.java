package com.kuqi.mall.feign.core.logger;

import com.alibaba.fastjson.JSON;
import com.kuqi.mall.feign.core.HttpLogHolder;
import com.kuqi.mall.feign.core.entity.HttpLog;
import com.kuqi.mall.feign.core.utils.HttpLogUtil;
import feign.Request;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 20:10
 **/
@Slf4j
public class Slf4jFeignLogger extends feign.Logger {

    private final Logger logger;

    public Slf4jFeignLogger(Class<?> clazz) {
        this(LoggerFactory.getLogger(clazz));
    }

    public Slf4jFeignLogger(String name) {
        this(LoggerFactory.getLogger(name));
    }

    public Slf4jFeignLogger() {
        this(feign.Logger.class);
    }

    public Slf4jFeignLogger(Logger logger) {
        this.logger = logger;
    }


    @Override
    protected void log(String configKey, String format, Object... args) {

        if (logger.isInfoEnabled()) {

            logger.info(String.format(methodTag(configKey), args) + format);
        }
    }

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {

        Map<String, Collection<String>> headers = request.headers();
        HttpLog httpLog = HttpLog.builder()
                .headers(MapUtils.isNotEmpty(headers) ? JSON.toJSONString(headers) : StringUtils.EMPTY)
                .methodName(request.httpMethod().name())
                .request(this.getBodyText(request))
                .url(request.url())
                .startAt(LocalDateTime.now())
                .build();
        HttpLogHolder.set(httpLog);
    }

    private String getBodyText(Request request) {

        byte[] body;
        if (ArrayUtils.isNotEmpty(body = request.body())) {

            Charset charset;
            return Objects.nonNull(charset = request.charset()) ? new String(body, charset) : null;
        }
        return null;
    }


    @Override
    protected Response logAndRebufferResponse(String configKey,
                                              Level logLevel,
                                              Response response,
                                              long elapsedTime) throws IOException {

        HttpLog httpLog = HttpLogHolder.getAndRelease();
        if (Objects.nonNull(httpLog)) {

            httpLog.setFinishAt(LocalDateTime.now());
            httpLog.setDuration(httpLog.getFinishAt().toInstant(ZoneOffset.UTC).toEpochMilli() - httpLog.getStartAt().toInstant(ZoneOffset.UTC).toEpochMilli());
            httpLog.setStatus(response.status());

            String msg;
            switch (logLevel) {

                case FULL:

                    String bodyData;
                    if (StringUtils.isNotEmpty(bodyData = IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8.name()))) {

                        // 从response中获取数据
                        httpLog.setResponse(bodyData);
                        response = response.toBuilder().body(bodyData, StandardCharsets.UTF_8).build();
                    }
                    msg = String.format("url: [%s],methodName: [%s],startAt: [%s],finishAt: [%s],duration: [%sms],status: [%s],headers:[%s],request: [%s],response: [%s]"
                            , httpLog.getUrl(), httpLog.getMethodName(),
                            getMills(httpLog.getStartAt()), getMills(httpLog.getFinishAt()), httpLog.getDuration(), httpLog.getStatus(),
                            httpLog.getHeaders(), httpLog.getRequest(), HttpLogUtil.subStringIfRequired(httpLog.getResponse()));

                    break;
                default:
                    msg = String.format("url: [%s],methodName: [%s],startAt: [%s],finishAt: [%s],duration: [%sms],status: [%s],headers:[%s]"
                            , httpLog.getUrl(), httpLog.getMethodName(),
                            getMills(httpLog.getStartAt()), getMills(httpLog.getFinishAt()), httpLog.getDuration(), httpLog.getStatus(),
                            httpLog.getHeaders());
                    break;
            }
            log(configKey, "----------------- feign log -----------------");
            log(configKey, msg);
        }
        return response;
    }

    private long getMills(LocalDateTime dateTime) {

        if (Objects.isNull(dateTime)) {
            return 0;
        }
        return dateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
}

