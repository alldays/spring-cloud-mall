package com.kuqi.mall.system.entity.query;

import com.kuqi.mall.system.entity.enums.MenuType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 17:59
 **/
@Data
@ApiModel("菜单列表查询")
public class MenuListQuery implements Serializable {
    private static final long serialVersionUID = -2682314034724743323L;

    @ApiModelProperty("菜单名称")
    private String name;
    /**
     * 类型（1:目录2:菜单3:按钮4:url）
     * {@link MenuType}
     */
    @ApiModelProperty("类型（1:目录2:菜单3:按钮4:url）")
    private Integer type;

    private List<Long> ids;
}
