package com.kuqi.mall.web.core.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 16:11
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 6760866195738201448L;
    /**
     * 业务错误码
     */
    private Integer code;
    /**
     * 错误提示
     */
    private String message;
}
