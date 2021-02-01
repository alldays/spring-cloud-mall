package com.kuqi.mall.system.entity.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 10:28
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleQuery implements Serializable {

    private static final long serialVersionUID = -3991083935319755923L;

    private Long userId;

    private Long roleId;
}
