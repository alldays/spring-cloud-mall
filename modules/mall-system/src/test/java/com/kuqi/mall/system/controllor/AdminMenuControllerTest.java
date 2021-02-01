package com.kuqi.mall.system.controllor;

import com.kuqi.mall.system.BaseTest;
import com.kuqi.mall.system.MallSystemApplication;
import com.kuqi.mall.system.entity.dto.SaveMenuDto;
import com.kuqi.mall.system.entity.dto.UpdateMenuDto;
import com.kuqi.mall.system.entity.enums.MenuMethod;
import com.kuqi.mall.system.entity.enums.MenuStatus;
import com.kuqi.mall.system.entity.enums.MenuType;
import com.kuqi.mall.system.entity.enums.MenuVisible;
import com.kuqi.mall.system.entity.vo.MenuVo;
import com.kuqi.mall.web.core.entity.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 21:55
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MallSystemApplication.class)
@Slf4j
@AutoConfigureMockMvc
public class AdminMenuControllerTest extends BaseTest {

    /**
     * 成功新增菜单
     */
    @Test
    public void success2save() throws Exception {

        SaveMenuDto saveMenuDto = new SaveMenuDto();
        saveMenuDto.setName("重置密码");
        saveMenuDto.setParentId(1355339254819966978l);
        saveMenuDto.setOrderNum(4);
        saveMenuDto.setType(MenuType.button.getValue());
        saveMenuDto.setVisible(MenuVisible.show.getValue());
        saveMenuDto.setUrl("/admin/user/{id}/reset-pwd");
        saveMenuDto.setMethod(MenuMethod.put.getValue());
        saveMenuDto.setPerms("user:reset-pwd");
        Response<MenuVo> response = super.saveMenu(saveMenuDto);
        Assert.assertNotNull(response);
        MenuVo menuVo;
        Assert.assertNotNull(menuVo = response.getData());
        Assert.assertEquals(menuVo.getName(),saveMenuDto.getName());
        Assert.assertEquals(menuVo.getOrderNum(),saveMenuDto.getOrderNum());
        Assert.assertEquals(menuVo.getType(),saveMenuDto.getType());
        Assert.assertEquals(menuVo.getVisible(),saveMenuDto.getVisible());
        Assert.assertEquals(menuVo.getStatus(),MenuStatus.normal.getValue());
        Assert.assertEquals(menuVo.getUrl(),saveMenuDto.getUrl());
        Assert.assertEquals(menuVo.getPerms(),saveMenuDto.getPerms());
        Assert.assertEquals(menuVo.getMethod(), saveMenuDto.getMethod());
    }

    /**
     * 成功修改菜单
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
        Response<MenuVo> response = super.saveMenu(saveMenuDto);
        Assert.assertNotNull(response);
        MenuVo menuVo;
        Assert.assertNotNull(menuVo = response.getData());


        UpdateMenuDto updateMenuDto = new UpdateMenuDto();
        BeanUtils.copyProperties(menuVo, updateMenuDto);

        response = super.updateMenu(updateMenuDto);
        Assert.assertNotNull(response);
        Assert.assertNotNull(menuVo = response.getData());
        Assert.assertEquals(menuVo.getName(), updateMenuDto.getName());
        Assert.assertEquals(menuVo.getOrderNum(), updateMenuDto.getOrderNum());
        Assert.assertEquals(menuVo.getType(), updateMenuDto.getType());
        Assert.assertEquals(menuVo.getVisible(), updateMenuDto.getVisible());
        Assert.assertEquals(menuVo.getUrl(), updateMenuDto.getUrl());
        Assert.assertEquals(menuVo.getPerms(), updateMenuDto.getPerms());
        Assert.assertEquals(menuVo.getMethod(), updateMenuDto.getMethod());
    }

    /**
     * 成功删除菜单
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
        Response<MenuVo> response = super.saveMenu(saveMenuDto);
        Assert.assertNotNull(response);
        MenuVo menuVo;
        Assert.assertNotNull(menuVo = response.getData());

        Response<Boolean> deleteResponse = super.deleteMenu(menuVo.getId());
        Assert.assertNotNull(deleteResponse);
        Assert.assertTrue(deleteResponse.getData());
    }
}
