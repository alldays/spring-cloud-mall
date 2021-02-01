package com.kuqi.mall.web.core.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 16:08
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 3685616787397099481L;
    private int status;

    private String code;

    private String msg;

    private T data;

    public static <T> Response<T> error(String message) {
        return error(null, message);
    }

    public static <T> Response<T> error(String code, String message) {

        Response<T> error = new Response<T>();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setCode(code);
        error.setMsg(message);
        return error;
    }

    public static <T> Response<T> success(T data) {

        Response<T> success = new Response<T>();
        success.setStatus(HttpStatus.OK.value());
        success.setData(data);
        return success;
    }
}
