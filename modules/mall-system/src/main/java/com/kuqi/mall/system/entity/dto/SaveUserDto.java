package com.kuqi.mall.system.entity.dto;

import com.kuqi.maill.common.core.validation.EffectiveEnum;
import com.kuqi.maill.common.core.validation.EffectivePhone;
import com.kuqi.maill.common.core.validation.EffectiveValue;
import com.kuqi.mall.system.entity.enums.UserSex;
import com.kuqi.mall.system.entity.enums.UserType;
import com.kuqi.mall.system.core.validator.IdsOfRoleValidator;
import com.kuqi.mall.system.core.validator.NameOfUserNotExistValidator;
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
 * @Date 2021/1/28 22:14
 **/
@Data
@ApiModel("新增用户dto")
@EffectiveValue(shouldBeNull = true, message = USER_NAME_IS_INVALID, serviceBean = NameOfUserNotExistValidator.class)
public class SaveUserDto implements Serializable {

    private static final long serialVersionUID = -7614497634777214606L;

    @NotEmpty(message = USER_NAME_IS_EMPTY)
    @ApiModelProperty("用户账号")
    private String userName;

    @ApiModelProperty("用户昵称")
    private String nickName;

    /**
     * 用户类型（0:管理员1:普通用户）
     * {@link UserType}
     */
    @ApiModelProperty(value = "用户类型（0:管理员1:普通用户）", hidden = true)
    private Integer type = 0;

    @ApiModelProperty("用户邮箱")
    private String email;

    @NotEmpty(message = USER_PHONE_IS_EMPTY)
    @EffectivePhone(shouldBeNull = true, message = USER_PHONE_IS_INVALID)
    @ApiModelProperty("手机号码")
    private String phone;

    /**
     * 性别
     * {@link UserSex}
     */
    @NotNull(message = USER_SEX_IS_NULL)
    @EffectiveEnum(shouldBeNull = true, enumClass = UserSex.class, getterMethodName = "valueOf")
    @ApiModelProperty("用户性别（0男 1女 2未知）")
    private Integer sex;

    @ApiModelProperty("头像地址")
    private String avatar;

    @ApiModelProperty("密码")
    private String password;

    @NotEmpty(message = ROLE_ID_IS_NULL)
    @EffectiveValue(shouldBeNull = true, message = ROLE_ID_IS_INVALID, serviceBean = IdsOfRoleValidator.class)
    @ApiModelProperty("用户角色id")
    private List<Long> roleIdList;
}
