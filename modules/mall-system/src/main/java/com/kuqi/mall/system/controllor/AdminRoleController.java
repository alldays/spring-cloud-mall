package com.kuqi.mall.system.controllor;

import com.kuqi.mall.security.core.aop.VerifyLoginUser;
import com.kuqi.mall.system.entity.dto.SaveRoleDto;
import com.kuqi.mall.system.entity.dto.UpdateRoleDto;
import com.kuqi.mall.system.entity.vo.RoleVo;
import com.kuqi.mall.system.manager.IRoleManager;
import com.kuqi.mall.web.core.entity.vo.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kuqi.mall.security.core.aop.VerifyLoginUser.INVALID_ADMIN_TYPE;
import static com.kuqi.mall.security.core.aop.VerifyLoginUser.IS_ADMIN;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 20:13
 **/
@Api(tags = "管理员角色api")
@RestController
public class AdminRoleController {

    @Autowired
    private IRoleManager roleManager;

    @PreAuthorize("hasAnyAuthority('role:list:admin','admin')")
    @ApiOperation(value = "角色列表")
    @GetMapping("/admin/role")
    @VerifyLoginUser(type = IS_ADMIN, errorMsg = INVALID_ADMIN_TYPE)
    public Response<List<RoleVo>> list() {
        return Response.success(null);
    }

    @PreAuthorize("hasAnyAuthority('role:add','admin')")
    @ApiOperation(value = "新增角色")
    @PostMapping("/admin/role/add")
    @VerifyLoginUser(type = IS_ADMIN, errorMsg = INVALID_ADMIN_TYPE)
    public Response<RoleVo> save(@Validated @RequestBody SaveRoleDto saveRoleDto) {
        return Response.success(roleManager.save(saveRoleDto));
    }

    @PreAuthorize("hasAnyAuthority('role:update','admin')")
    @ApiOperation(value = "修改角色")
    @PutMapping("/admin/role/update")
    @VerifyLoginUser(type = IS_ADMIN, errorMsg = INVALID_ADMIN_TYPE)
    public Response<RoleVo> update(@Validated @RequestBody UpdateRoleDto updateRoleDto) {
        return Response.success(roleManager.update(updateRoleDto));
    }

    @PreAuthorize("hasAnyAuthority('role:delete','admin')")
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/admin/role/{id}")
    @VerifyLoginUser(type = IS_ADMIN, errorMsg = INVALID_ADMIN_TYPE)
    public Response<Boolean> delete(@PathVariable("id") Long id) {
        return Response.success(roleManager.delete(id));
    }
}
