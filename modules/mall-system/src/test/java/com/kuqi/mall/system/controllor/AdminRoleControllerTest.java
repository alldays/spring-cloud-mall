package com.kuqi.mall.system.controllor;

import com.kuqi.mall.system.BaseTest;
import com.kuqi.mall.system.MallSystemApplication;
import com.kuqi.mall.system.entity.dto.SaveMenuDto;
import com.kuqi.mall.system.entity.dto.SaveRoleDto;
import com.kuqi.mall.system.entity.dto.UpdateRoleDto;
import com.kuqi.mall.system.entity.enums.MenuMethod;
import com.kuqi.mall.system.entity.enums.MenuType;
import com.kuqi.mall.system.entity.enums.MenuVisible;
import com.kuqi.mall.system.entity.enums.RoleStatus;
import com.kuqi.mall.system.entity.vo.MenuVo;
import com.kuqi.mall.system.entity.vo.RoleVo;
import com.kuqi.mall.web.core.entity.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 20:29
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MallSystemApplication.class)
@Slf4j
@AutoConfigureMockMvc
public class AdminRoleControllerTest extends BaseTest {

    /**
     * 成功新增角色
     */
    @Test
    public void success2save() throws Exception {

        SaveMenuDto saveMenuDto = new SaveMenuDto();
        saveMenuDto.setName("菜单列表");
        saveMenuDto.setOrderNum(1);
        saveMenuDto.setType(MenuType.contents.getValue());
        saveMenuDto.setVisible(MenuVisible.show.getValue());
        saveMenuDto.setUrl("/admin/menu/save");
        saveMenuDto.setMethod(MenuMethod.post.getValue());
        saveMenuDto.setPerms("menu:operation");
        Response<MenuVo> menuResponse = super.saveMenu(saveMenuDto);
        Assert.assertNotNull(menuResponse);
        MenuVo menuVo;
        Assert.assertNotNull(menuVo = menuResponse.getData());

        SaveRoleDto saveRoleDto = new SaveRoleDto();
        saveRoleDto.setName("超级管理员" + UUID.randomUUID());
        saveRoleDto.setSort(1);
        saveRoleDto.setMenuIdList(Lists.newArrayList(menuVo.getId()));
        Response<RoleVo> response = super.saveRole(saveRoleDto);
        Assert.assertNotNull(response);
        RoleVo roleVo;
        Assert.assertNotNull(roleVo = response.getData());

        Assert.assertEquals(roleVo.getName(), saveRoleDto.getName());
        Assert.assertEquals(roleVo.getSort(), saveRoleDto.getSort());
        Assert.assertEquals(roleVo.getStatus(), RoleStatus.normal.getValue());
    }

    /**
     * 成功修改角色
     */
    @Test
    public void success2update() throws Exception {

        SaveMenuDto saveMenuDto = new SaveMenuDto();
        saveMenuDto.setName("菜单列表");
        saveMenuDto.setOrderNum(1);
        saveMenuDto.setType(MenuType.contents.getValue());
        saveMenuDto.setVisible(MenuVisible.show.getValue());
        saveMenuDto.setUrl("/admin/menu/save");
        saveMenuDto.setMethod(MenuMethod.post.getValue());
        saveMenuDto.setPerms("menu:operation");
        Response<MenuVo> menuResponse = super.saveMenu(saveMenuDto);
        Assert.assertNotNull(menuResponse);
        MenuVo menuVo;
        Assert.assertNotNull(menuVo = menuResponse.getData());

        SaveRoleDto saveRoleDto = new SaveRoleDto();
        saveRoleDto.setName("超级管理员" + UUID.randomUUID());
        saveRoleDto.setSort(1);
        saveRoleDto.setMenuIdList(Lists.newArrayList(menuVo.getId()));
        Response<RoleVo> response = super.saveRole(saveRoleDto);
        Assert.assertNotNull(response);
        RoleVo roleVo;
        Assert.assertNotNull(roleVo = response.getData());


        menuResponse = super.saveMenu(saveMenuDto);
        Assert.assertNotNull(menuResponse);
        Assert.assertNotNull(menuVo = menuResponse.getData());

        // 修改角色
        UpdateRoleDto updateRoleDto = new UpdateRoleDto();
        updateRoleDto.setId(roleVo.getId());
        updateRoleDto.setName(roleVo.getName() + "update");
        updateRoleDto.setSort(2);
        updateRoleDto.setMenuIdList(Lists.newArrayList(menuVo.getId()));
        Response<RoleVo> updateResponse = super.updateRole(updateRoleDto);
        Assert.assertNotNull(updateResponse);

        RoleVo updatedRoleVo;
        Assert.assertNotNull(updatedRoleVo = updateResponse.getData());
        Assert.assertEquals(updatedRoleVo.getName(), updateRoleDto.getName());
        Assert.assertEquals(updatedRoleVo.getSort(), updateRoleDto.getSort());
        Assert.assertEquals(updatedRoleVo.getStatus(), RoleStatus.normal.getValue());
    }

    /**
     * 成功删除角色
     */
    @Test
    public void success2delete() throws Exception {

        SaveMenuDto saveMenuDto = new SaveMenuDto();
        saveMenuDto.setName("菜单列表");
        saveMenuDto.setOrderNum(1);
        saveMenuDto.setType(MenuType.contents.getValue());
        saveMenuDto.setVisible(MenuVisible.show.getValue());
        saveMenuDto.setUrl("/admin/menu/save");
        saveMenuDto.setMethod(MenuMethod.post.getValue());
        saveMenuDto.setPerms("menu:operation");
        Response<MenuVo> menuResponse = super.saveMenu(saveMenuDto);
        Assert.assertNotNull(menuResponse);
        MenuVo menuVo;
        Assert.assertNotNull(menuVo = menuResponse.getData());

        SaveRoleDto saveRoleDto = new SaveRoleDto();
        saveRoleDto.setName("超级管理员" + UUID.randomUUID());
        saveRoleDto.setSort(1);
        saveRoleDto.setMenuIdList(Lists.newArrayList(menuVo.getId()));

        Response<RoleVo> response = super.saveRole(saveRoleDto);
        Assert.assertNotNull(response);
        RoleVo roleVo;
        Assert.assertNotNull(roleVo = response.getData());
        
        Response<Boolean> deleteResponse = super.deleteRole(roleVo.getId());
        Assert.assertNotNull(deleteResponse);
        Assert.assertTrue(deleteResponse.getData());
    }
}
