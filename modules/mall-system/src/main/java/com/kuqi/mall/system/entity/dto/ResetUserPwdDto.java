package com.kuqi.mall.system.entity.dto;

import com.kuqi.maill.common.core.validation.EffectiveValue;
import com.kuqi.mall.system.core.validator.IdOfUserValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static com.kuqi.mall.system.entity.constants.ExceptionConstant.*;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 16:30
 **/
@Data
@ApiModel("重置密码dto")
public class ResetUserPwdDto implements Serializable {
    private static final long serialVersionUID = 7401119943964892987L;

    @EffectiveValue(message = USER_ID_IS_INVALID, serviceBean = IdOfUserValidator.class)
    @NotNull(message = USER_ID_IS_NULL)
    @ApiModelProperty("id")
    private Long id;

    @NotEmpty(message = USER_NEW_PWD_IS_EMPTY)
    @ApiModelProperty("新密码")
    private String newPwd;
}
