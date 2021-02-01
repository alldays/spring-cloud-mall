package com.kuqi.mall.system.entity.vo;

import com.kuqi.mall.system.entity.enums.RoleStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 20:28
 **/
@Data
@ApiModel("角色vo")
public class RoleVo implements Serializable {
    private static final long serialVersionUID = 7025171652033949255L;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色排序")
    private Integer sort;

    /**
     * 角色状态（0正常 1停用）
     * {@link RoleStatus}
     */
    @ApiModelProperty("角色状态（0正常 1停用）")
    private Integer status;

    @ApiModelProperty("所属菜单")
    private List<MenuVo> menuList;
}
