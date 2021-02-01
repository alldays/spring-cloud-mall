package com.kuqi.mall.system.entity.dto;

import com.kuqi.maill.common.core.validation.EffectiveEnum;
import com.kuqi.maill.common.core.validation.EffectiveValue;
import com.kuqi.mall.system.entity.enums.MenuMethod;
import com.kuqi.mall.system.entity.enums.MenuType;
import com.kuqi.mall.system.entity.enums.MenuVisible;
import com.kuqi.mall.system.core.validator.IdOfMenuValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static com.kuqi.mall.system.entity.constants.ExceptionConstant.*;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 17:02
 **/
@Data
@ApiModel("修改菜单dto")
public class UpdateMenuDto implements Serializable {
    private static final long serialVersionUID = -2229420990495819500L;

    @EffectiveValue(message = MENU_ID_IS_INVALID, serviceBean = IdOfMenuValidator.class)
    @NotNull(message = MENU_ID_IS_NULL)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("菜单名称")
    private String name;

    @EffectiveValue(shouldBeNull = true, message = MENU_PID_IS_INVALID, serviceBean = IdOfMenuValidator.class)
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
    @EffectiveEnum(shouldBeNull = true, enumClass = MenuMethod.class, getterMethodName = "valueOf")
    @ApiModelProperty("url请求方法")
    private Integer method;

    /**
     * 类型（1:目录2:菜单3:按钮4:url）
     * {@link MenuType}
     */
    @EffectiveEnum(shouldBeNull = true, enumClass = MenuType.class, getterMethodName = "valueOf")
    @ApiModelProperty("类型（1:目录2:菜单3:按钮4:url）")
    private Integer type;

    /**
     * 菜单状态（0显示 1隐藏）
     * {@link MenuVisible}
     */
    @EffectiveEnum(shouldBeNull = true, enumClass = MenuVisible.class, getterMethodName = "valueOf")
    @ApiModelProperty("菜单状态（0显示 1隐藏）")
    private Integer visible;

    @ApiModelProperty("权限字符串")
    private String perms;

    @ApiModelProperty("菜单图标")
    private String icon;
}
