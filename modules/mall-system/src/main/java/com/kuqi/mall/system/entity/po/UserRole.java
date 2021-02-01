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
 * @Date 2021/1/29 10:14
 **/
@Accessors(chain = true)
@TableName(value = "user_role", autoResultMap = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements Serializable {
    private static final long serialVersionUID = 734341177738201633L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "role_id")
    private Long roleId;
}
