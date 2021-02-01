package com.kuqi.mall.log.core.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 15:28
 **/
@Builder
@Data
public class HttpLog implements Serializable {

    private static final long serialVersionUID = -7653679960713897538L;
    /**
     * 全局请求id
     */
    private String traceId;
    /**
     * 服务url
     */
    private String url;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 方法参数
     */
    private String request;
    /**
     * 请求头参数
     */
    private String headers;
    /**
     * 请求开始时间
     */
    private LocalDateTime startAt;
    /**
     * 请求结束时间
     */
    private LocalDateTime finishAt;
    /**
     * 执行时间
     */
    private long duration;
    /**
     * 状态
     */
    private int status;
    /**
     * 返回结果
     */
    private String response;
}
