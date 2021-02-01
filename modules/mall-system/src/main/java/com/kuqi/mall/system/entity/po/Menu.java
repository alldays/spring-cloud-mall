package com.kuqi.mall.system.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kuqi.mall.mybatis.core.entity.BaseEntity;
import com.kuqi.mall.system.entity.enums.MenuMethod;
import com.kuqi.mall.system.entity.enums.MenuStatus;
import com.kuqi.mall.system.entity.enums.MenuType;
import com.kuqi.mall.system.entity.enums.MenuVisible;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 21:35
 **/
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "menu", autoResultMap = true)
@Data
public class Menu extends BaseEntity {

    /**
     * 菜单名称
     */
    @TableField("name")
    private String name;

    /**
     * 父菜单ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 显示顺序
     */
    @TableField("order_num")
    private String orderNum;

    /**
     * 菜单URL
     */
    @TableField("url")
    private String url;

    /**
     * url请求方法
     * {@link MenuMethod}
     */
    @TableField("method")
    private Integer method;

    /**
     * 类型（1:目录2:菜单3:按钮4:url）
     * {@link MenuType}
     */
    @TableField("type")
    private Integer type;

    /**
     * 菜单状态（0显示 1隐藏）
     * {@link MenuVisible}
     */
    @TableField("visible")
    private Integer visible;

    /**
     * 菜单状态（0正常 1停用）
     * {@link MenuStatus}
     */
    @TableField("status")
    private Integer status;

    /**
     * 权限字符串
     */
    @TableField("perms")
    private String perms;

    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
