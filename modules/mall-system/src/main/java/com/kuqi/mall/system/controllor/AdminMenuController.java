package com.kuqi.mall.system.controllor;

import com.kuqi.mall.security.core.aop.VerifyLoginUser;
import com.kuqi.mall.system.entity.dto.SaveMenuDto;
import com.kuqi.mall.system.entity.dto.UpdateMenuDto;
import com.kuqi.mall.system.entity.query.MenuListQuery;
import com.kuqi.mall.system.entity.vo.MenuVo;
import com.kuqi.mall.system.manager.IMenuManager;
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
 * @Date 2021/1/27 21:32
 **/
@Api(tags = "管理员菜单api")
@RestController
public class AdminMenuController {

    @Autowired
    private IMenuManager menuManager;

    @PreAuthorize("hasAnyAuthority('menu:list:admin','admin')")
    @ApiOperation(value = "获取所有菜单")
    @GetMapping("/admin/menu")
    @VerifyLoginUser(type = IS_ADMIN, errorMsg = INVALID_ADMIN_TYPE)
    public Response<List<MenuVo>> list(MenuListQuery menuListQuery) {
        return Response.success(menuManager.list(menuListQuery));
    }

    @PreAuthorize("hasAnyAuthority('menu:add','admin')")
    @ApiOperation(value = "新增菜单")
    @PostMapping("/admin/menu/add")
    @VerifyLoginUser(type = IS_ADMIN, errorMsg = INVALID_ADMIN_TYPE)
    public Response<MenuVo> save(@Validated @RequestBody SaveMenuDto saveMenuDto) {
        return Response.success(menuManager.save(saveMenuDto));
    }

    @PreAuthorize("hasAnyAuthority('menu:update','admin')")
    @ApiOperation(value = "修改菜单")
    @PutMapping("/admin/menu/update")
    @VerifyLoginUser(type = IS_ADMIN, errorMsg = INVALID_ADMIN_TYPE)
    public Response<MenuVo> update(@Validated @RequestBody UpdateMenuDto updateMenuDto) {
        return Response.success(menuManager.update(updateMenuDto));
    }

    @PreAuthorize("hasAnyAuthority('menu:delete','admin')")
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/admin/menu/{id}")
    @VerifyLoginUser(type = IS_ADMIN, errorMsg = INVALID_ADMIN_TYPE)
    public Response<Boolean> update(@PathVariable("id") Long id) {
        return Response.success(menuManager.delete(id));
    }
}
