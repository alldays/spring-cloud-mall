package com.kuqi.mall.system.entity.dto;

import com.kuqi.maill.common.core.validation.EffectiveValue;
import com.kuqi.mall.system.core.validator.IdOfRoleValidator;
import com.kuqi.mall.system.core.validator.IdsOfMenuValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

import static com.kuqi.mall.system.entity.constants.ExceptionConstant.*;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 20:59
 **/
@Data
@ApiModel("修改角色dto")
public class UpdateRoleDto implements Serializable {

    private static final long serialVersionUID = -6085957899809104581L;

    @EffectiveValue(message = ROLE_ID_IS_INVALID, serviceBean = IdOfRoleValidator.class)
    @NotNull(message = ROLE_ID_IS_NULL)
    @ApiModelProperty("id")
    private Long id;
    /**
     * 角色名称
     */
    @ApiModelProperty("name")
    private String name;

    /**
     * 角色排序
     */
    @ApiModelProperty("sort")
    private Integer sort;

    @EffectiveValue(shouldBeNull = true, message = MENU_ID_IS_INVALID, serviceBean = IdsOfMenuValidator.class)
    @ApiModelProperty("所属菜单")
    private List<Long> menuIdList;
}
