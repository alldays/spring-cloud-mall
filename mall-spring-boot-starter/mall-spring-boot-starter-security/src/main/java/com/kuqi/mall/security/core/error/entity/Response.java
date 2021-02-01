package com.kuqi.mall.security.core.error.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author iloveoverfly
 * @Date 2021/1/31 22:49
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 3685616787397099481L;
    private Integer status;

    private String code;

    private String msg;

    private T data;

    public static <T> Response<T> error(String message, Integer status) {

        Response<T> error = new Response<T>();
        error.setStatus(status);
        error.setMsg(message);
        return error;
    }
}
