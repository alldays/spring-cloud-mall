package com.kuqi.mall.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.kuqi.maill.common.core.constant.GlobalConstant;
import com.kuqi.mall.system.entity.dto.*;
import com.kuqi.mall.system.entity.query.MenuListQuery;
import com.kuqi.mall.system.entity.vo.MenuVo;
import com.kuqi.mall.system.entity.vo.RoleVo;
import com.kuqi.mall.system.entity.vo.UserVo;
import com.kuqi.mall.web.core.entity.vo.Response;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.util.List;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 21:58
 **/
public class BaseTest {


    protected static final TypeReference<Response<MenuVo>> menuVoTypeReference = new TypeReference<Response<MenuVo>>() {
    };

    protected static final TypeReference<Response<List<MenuVo>>> menuVoListTypeReference = new TypeReference<Response<List<MenuVo>>>() {
    };

    protected static final TypeReference<Response<Boolean>> booleanTypeReference = new TypeReference<Response<Boolean>>() {
    };

    protected static final TypeReference<Response<RoleVo>> roleTypeReference = new TypeReference<Response<RoleVo>>() {
    };

    protected static final TypeReference<Response<UserVo>> userVoTypeReference = new TypeReference<Response<UserVo>>() {
    };


    protected MockMvc mockMvc;

    /**
     * spring security 过滤器
     */
    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();
    }

    /**
     * 查询菜单
     */
    protected Response<List<MenuVo>> listMenu(MenuListQuery menuListQuery) throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/menu")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JSON.toJSONString(menuListQuery))
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(GlobalConstant.AUTHORIZATION_HEADER, GlobalConstant.ADMIN_TOKEN))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        return JSON.parseObject(mvcResult.getResponse().getContentAsString(), menuVoListTypeReference);
    }

    /**
     * 保存菜单
     */
    protected Response<MenuVo> saveMenu(SaveMenuDto saveMenuDto) throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/admin/menu/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JSON.toJSONString(saveMenuDto))
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(GlobalConstant.AUTHORIZATION_HEADER, GlobalConstant.ADMIN_TOKEN))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        return JSON.parseObject(mvcResult.getResponse().getContentAsString(), menuVoTypeReference);
    }

    /**
     * 修改菜单
     */
    protected Response<MenuVo> updateMenu(UpdateMenuDto updateMenuDto) throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put("/admin/menu/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JSON.toJSONString(updateMenuDto))
                .header(GlobalConstant.AUTHORIZATION_HEADER, GlobalConstant.ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        return JSON.parseObject(mvcResult.getResponse().getContentAsString(), menuVoTypeReference);
    }

    /**
     * 删除菜单
     */
    protected Response<Boolean> deleteMenu(Long id) throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/admin/menu/" + id)
                .header(GlobalConstant.AUTHORIZATION_HEADER, GlobalConstant.ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        return JSON.parseObject(mvcResult.getResponse().getContentAsString(), booleanTypeReference);
    }

    /**
     * 保存角色
     */
    protected Response<RoleVo> saveRole(SaveRoleDto saveRoleDto) throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/admin/role/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JSON.toJSONString(saveRoleDto))
                .header(GlobalConstant.AUTHORIZATION_HEADER, GlobalConstant.ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        return JSON.parseObject(mvcResult.getResponse().getContentAsString(), roleTypeReference);
    }

    /**
     * 修改角色
     */
    protected Response<RoleVo> updateRole(UpdateRoleDto updateRoleDto) throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put("/admin/role/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JSON.toJSONString(updateRoleDto))
                .header(GlobalConstant.AUTHORIZATION_HEADER, GlobalConstant.ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        return JSON.parseObject(mvcResult.getResponse().getContentAsString(), roleTypeReference);
    }

    /**
     * 删除角色
     */
    protected Response<Boolean> deleteRole(Long id) throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/admin/role/" + id)
                .header(GlobalConstant.AUTHORIZATION_HEADER, GlobalConstant.ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        return JSON.parseObject(mvcResult.getResponse().getContentAsString(), booleanTypeReference);
    }

    /**
     * 保存用户
     */
    protected Response<UserVo> getAdminUser(Long id) throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/user/" + id)
                .header(GlobalConstant.AUTHORIZATION_HEADER, GlobalConstant.ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        return JSON.parseObject(mvcResult.getResponse().getContentAsString(), userVoTypeReference);
    }

    /**
     * 保存用户
     */
    protected Response<UserVo> saveAdminUser(SaveUserDto saveUserDto) throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/admin/user/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JSON.toJSONString(saveUserDto))
                .header(GlobalConstant.AUTHORIZATION_HEADER, GlobalConstant.ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        return JSON.parseObject(mvcResult.getResponse().getContentAsString(), userVoTypeReference);
    }

    /**
     * 修改用户
     */
    protected Response<UserVo> updateAdminUser(UpdateUserDto updateUserDto) throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put("/admin/user/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JSON.toJSONString(updateUserDto))
                .header(GlobalConstant.AUTHORIZATION_HEADER, GlobalConstant.ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        return JSON.parseObject(mvcResult.getResponse().getContentAsString(), userVoTypeReference);
    }

    /**
     * 修改用户密码
     */
    protected Response<UserVo> resetAdminUserPwd(ResetUserPwdDto resetUserPwdDto) throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put("/admin/user/" + resetUserPwdDto.getId() + "/reset-pwd")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JSON.toJSONString(resetUserPwdDto))
                .header(GlobalConstant.AUTHORIZATION_HEADER, GlobalConstant.ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        return JSON.parseObject(mvcResult.getResponse().getContentAsString(), userVoTypeReference);
    }

    /**
     * 删除用户
     */
    protected Response<Boolean> deleteUser(Long id) throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/admin/user/" + id)
                .header(GlobalConstant.AUTHORIZATION_HEADER, GlobalConstant.ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        return JSON.parseObject(mvcResult.getResponse().getContentAsString(), booleanTypeReference);
    }
}
