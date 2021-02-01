package com.kuqi.mall.system.entity.dto;

import com.kuqi.maill.common.core.validation.EffectiveEnum;
import com.kuqi.maill.common.core.validation.EffectivePhone;
import com.kuqi.maill.common.core.validation.EffectiveValue;
import com.kuqi.mall.system.entity.enums.UserSex;
import com.kuqi.mall.system.core.validator.IdOfUserValidator;
import com.kuqi.mall.system.core.validator.IdsOfRoleValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

import static com.kuqi.mall.system.entity.constants.ExceptionConstant.*;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 13:36
 **/
@Data
@ApiModel("修改用户dto")
public class UpdateUserDto implements Serializable {
    private static final long serialVersionUID = 493526094277351425L;

    @EffectiveValue(message = USER_ID_IS_INVALID, serviceBean = IdOfUserValidator.class)
    @NotNull(message = USER_ID_IS_NULL)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("用户邮箱")
    private String email;

    @EffectivePhone(shouldBeNull = true, message = USER_PHONE_IS_INVALID)
    @ApiModelProperty("手机号码")
    private String phone;

    /**
     * 性别
     * {@link UserSex}
     */
    @EffectiveEnum(shouldBeNull = true, enumClass = UserSex.class, getterMethodName = "valueOf")
    @ApiModelProperty("用户性别（0男 1女 2未知）")
    private Integer sex;

    @ApiModelProperty("头像地址")
    private String avatar;

    @EffectiveValue(shouldBeNull = true, message = ROLE_ID_IS_INVALID, serviceBean = IdsOfRoleValidator.class)
    @ApiModelProperty("用户角色id")
    private List<Long> roleIdList;
}
