package com.kuqi.mall.system.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 10:37
 **/
@Accessors(chain = true)
@TableName(value = "role_menu", autoResultMap = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenu implements Serializable {
    private static final long serialVersionUID = -7023788618555962837L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "menu_id")
    private Long menuId;

    @TableField(value = "role_id")
    private Long roleId;
}
