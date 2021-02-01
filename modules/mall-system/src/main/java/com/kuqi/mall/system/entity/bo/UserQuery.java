package com.kuqi.mall.system.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 11:00
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserQuery implements Serializable {
    private static final long serialVersionUID = 2249467479445177705L;

    private String userName;
    private Integer type;
}
