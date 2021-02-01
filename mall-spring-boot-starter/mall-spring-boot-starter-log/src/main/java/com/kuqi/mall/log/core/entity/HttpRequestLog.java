package com.kuqi.mall.log.core.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 15:29
 **/
@Data
@Builder
public class HttpRequestLog implements Serializable {
    private static final long serialVersionUID = -639632972875156826L;
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
    private String params;
}

