package com.kuqi.mall.system.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 20:41
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleQuery implements Serializable {
    private static final long serialVersionUID = -7262672323877168628L;

    /**
     * 角色名称
     */
    private String name;
}
