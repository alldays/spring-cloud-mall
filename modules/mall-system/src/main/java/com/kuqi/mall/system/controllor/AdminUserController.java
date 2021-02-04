package com.kuqi.mall.system.controllor;

import com.kuqi.mall.security.core.aop.VerifyLoginUser;
import com.kuqi.mall.system.entity.dto.ResetUserPwdDto;
import com.kuqi.mall.system.entity.dto.SaveUserDto;
import com.kuqi.mall.system.entity.dto.UpdateUserDto;
import com.kuqi.mall.system.entity.vo.UserVo;
import com.kuqi.mall.system.manager.IUserManager;
import com.kuqi.mall.web.core.entity.vo.PageData;
import com.kuqi.mall.web.core.entity.vo.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.kuqi.mall.security.core.aop.VerifyLoginUser.INVALID_ADMIN_TYPE;
import static com.kuqi.mall.security.core.aop.VerifyLoginUser.IS_ADMIN;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 22:14
 **/
@Api(tags = "管理员用户api")
@RestController
public class AdminUserController {

    @Autowired
    private IUserManager userManager;

    @PreAuthorize("hasAnyAuthority('user:list:admin','admin')")
    @ApiOperation(value = "用户列表")
    @GetMapping("/admin/user")
    @VerifyLoginUser(type = IS_ADMIN, errorMsg = INVALID_ADMIN_TYPE)
    public Response<PageData<UserVo>> page() {
        return Response.success(null);
    }

    @PreAuthorize("hasAnyAuthority('user:add','admin')")
    @ApiOperation(value = "新增用户")
    @PostMapping("/admin/user/add")
    @VerifyLoginUser(type = IS_ADMIN, errorMsg = INVALID_ADMIN_TYPE)
    public Response<UserVo> save(@Validated @RequestBody SaveUserDto saveUserDto) {
        return Response.success(userManager.save(saveUserDto));
    }

    @PreAuthorize("hasAnyAuthority('user:update','admin')")
    @ApiOperation(value = "修改用户")
    @PutMapping("/admin/user/update")
    @VerifyLoginUser(type = IS_ADMIN, errorMsg = INVALID_ADMIN_TYPE)
    public Response<UserVo> update(@Validated @RequestBody UpdateUserDto updateUserDto) {
        return Response.success(userManager.update(updateUserDto));
    }

    @PreAuthorize("hasAnyAuthority('user:reset-pwd','admin')")
    @ApiOperation(value = "用户重置密码")
    @PutMapping("/admin/user/{id}/reset-pwd")
    @VerifyLoginUser(type = IS_ADMIN, errorMsg = INVALID_ADMIN_TYPE)
    public Response<UserVo> resetPwd(@Validated @RequestBody ResetUserPwdDto resetUserPwdDto) {
        return Response.success(userManager.resetPwd(resetUserPwdDto));
    }

    @PreAuthorize("hasAnyAuthority('user:list:admin','admin')")
    @ApiOperation(value = "查询用户")
    @GetMapping("/admin/user/{id}")
    @VerifyLoginUser(type = IS_ADMIN, errorMsg = INVALID_ADMIN_TYPE)
    public Response<UserVo> getById(@PathVariable("id") Long id) {
        return Response.success(userManager.getById(id));
    }

    @PreAuthorize("hasAnyAuthority('user:reset-pwd','admin')")
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/admin/user/{id}")
    @VerifyLoginUser(type = IS_ADMIN, errorMsg = INVALID_ADMIN_TYPE)
    public Response<Boolean> resetPwd(@PathVariable("id") Long id) {
        return Response.success(userManager.delete(id));
    }
}
