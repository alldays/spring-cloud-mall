package com.kuqi.mall.system.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kuqi.mall.mybatis.core.entity.BaseEntity;
import com.kuqi.mall.system.entity.enums.RoleStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 20:16
 **/
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "role", autoResultMap = true)
@Data
public class Role extends BaseEntity {

    private static final long serialVersionUID = 901227943975678137L;
    /**
     * 角色名称
     */
    @TableField("name")
    private String name;

    /**
     * 角色排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 角色状态（0正常 1停用）
     * {@link RoleStatus}
     */
    @TableField("status")
    private Integer status;
}
