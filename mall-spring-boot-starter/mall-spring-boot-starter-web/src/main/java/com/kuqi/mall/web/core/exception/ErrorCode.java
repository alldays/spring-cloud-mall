package com.kuqi.mall.web.core.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 16:35
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorCode {

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误提示
     */
    private String message;

}
