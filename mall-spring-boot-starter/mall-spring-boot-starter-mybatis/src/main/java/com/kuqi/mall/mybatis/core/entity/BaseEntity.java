package com.kuqi.mall.mybatis.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author iloveoverfly
 * @Date 2021/1/25 18:23
 **/
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -4595595241615946621L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    protected Long id;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    protected LocalDateTime createTime;
    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updateTime;
    /**
     * 创建者
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    protected String createdBy;

    /**
     * 修改者;
     */
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    protected String updatedBy;

    /**
     * 版本号，用于乐观锁处理
     */
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    protected Integer version;

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    protected Integer delFlag;
}
