package com.kuqi.mall.system.entity.dto;

import com.kuqi.maill.common.core.validation.EffectiveValue;
import com.kuqi.mall.system.core.validator.IdsOfMenuValidator;
import com.kuqi.mall.system.core.validator.NameOfRoleNotExistValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

import static com.kuqi.mall.system.entity.constants.ExceptionConstant.*;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 20:14
 **/
@Data
@ApiModel("新增角色dto")
public class SaveRoleDto implements Serializable {
    private static final long serialVersionUID = -5199628782289938883L;

    @EffectiveValue(shouldBeNull = true, message = ROLE_NAME_IS_INVALID, serviceBean = NameOfRoleNotExistValidator.class)
    @NotEmpty(message = ROLE_NAME_IS_EMPTY)
    @ApiModelProperty("角色名称")
    private String name;

    @NotNull(message = ROLE_SORT_IS_NULL)
    @ApiModelProperty("角色排序")
    private Integer sort;

    @NotEmpty(message = MENU_ID_IS_NULL)
    @EffectiveValue(shouldBeNull = true, message = MENU_ID_IS_INVALID, serviceBean = IdsOfMenuValidator.class)
    @ApiModelProperty("所属菜单")
    private List<Long> menuIdList;
}
