package com.kuqi.mall.system.entity.vo;

import com.kuqi.mall.system.entity.enums.MenuMethod;
import com.kuqi.mall.system.entity.enums.MenuStatus;
import com.kuqi.mall.system.entity.enums.MenuType;
import com.kuqi.mall.system.entity.enums.MenuVisible;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 21:49
 **/
@Data
@ApiModel("菜单vo")
public class MenuVo implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = -7831653500297404490L;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("父菜单ID")
    private Long parentId;

    @ApiModelProperty("显示顺序")
    private Integer orderNum;

    @ApiModelProperty("菜单URL")
    private String url;

    /**
     * url请求方法
     * {@link MenuMethod}
     */
    @ApiModelProperty("url请求方法")
    private Integer method;

    /**
     * 类型（1:目录2:菜单3:按钮）
     * {@link MenuType}
     */
    @ApiModelProperty("类型（M目录 C菜单 F按钮）")
    private Integer type;

    /**
     * 菜单状态（0显示 1隐藏）
     * {@link MenuVisible}
     */
    @ApiModelProperty("菜单状态（0显示 1隐藏）")
    private Integer visible;

    /**
     * 菜单状态（0正常 1停用）
     * {@link MenuStatus}
     */
    @ApiModelProperty("菜单状态（0正常 1停用）")
    private Integer status;

    @ApiModelProperty("权限字符串")
    private String perms;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("删除标识")
    private Integer delFlag;

    /**
     * 权限字符串
     */
    @Override
    public String getAuthority() {
        return perms;
    }
}
