package com.kuqi.mall.system.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 10:44
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenuQuery implements Serializable {
    private static final long serialVersionUID = 5409872859180672810L;

    private Long menuId;

    private Long roleId;

    private Collection<Long> roleIds;

}
