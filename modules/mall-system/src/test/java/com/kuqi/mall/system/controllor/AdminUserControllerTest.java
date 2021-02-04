package com.kuqi.mall.system.controllor;

import com.kuqi.mall.system.BaseTest;
import com.kuqi.mall.system.MallSystemApplication;
import com.kuqi.mall.system.entity.dto.ResetUserPwdDto;
import com.kuqi.mall.system.entity.dto.SaveRoleDto;
import com.kuqi.mall.system.entity.dto.SaveUserDto;
import com.kuqi.mall.system.entity.dto.UpdateUserDto;
import com.kuqi.mall.system.entity.enums.UserSex;
import com.kuqi.mall.system.entity.enums.UserStatus;
import com.kuqi.mall.system.entity.vo.RoleVo;
import com.kuqi.mall.system.entity.vo.UserVo;
import com.kuqi.mall.web.core.entity.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 11:14
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MallSystemApplication.class)
@Slf4j
@AutoConfigureMockMvc
public class AdminUserControllerTest extends BaseTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void success2getAdminUser() throws Exception {

        Long userId = 1355025873780269057l;

        UserVo userVo;
        Response<UserVo> userResponse = this.getAdminUser(userId);
        Assert.assertNotNull(userVo = userResponse.getData());

        userResponse = this.getAdminUser(userId);
        Assert.assertNotNull(userVo = userResponse.getData());
    }

    @Test
    public void success2saveAdminUser() throws Exception {

        SaveRoleDto saveRoleDto = new SaveRoleDto();
        saveRoleDto.setName("超级管理员" + UUID.randomUUID());
        saveRoleDto.setSort(1);
        Response<RoleVo> response = super.saveRole(saveRoleDto);
        Assert.assertNotNull(response);
        RoleVo roleVo;
        Assert.assertNotNull(roleVo = response.getData());

        SaveUserDto saveUserDto = new SaveUserDto();
        saveUserDto.setPassword("123456");
        saveUserDto.setAvatar("https://www.baidu.com/");
        saveUserDto.setEmail("56456554@qq,com");
        saveUserDto.setNickName("阿里巴巴");
        saveUserDto.setPhone("15147477745");
        saveUserDto.setSex(UserSex.boy.getValue());
        saveUserDto.setUserName("alibaba" + UUID.randomUUID());
        saveUserDto.setRoleIdList(Lists.newArrayList(roleVo.getId()));

        Response<UserVo> userResponse = super.saveAdminUser(saveUserDto);
        Assert.assertNotNull(userResponse);
        UserVo userVo;
        Assert.assertNotNull(userVo = userResponse.getData());

        Assert.assertEquals(userVo.getStatus(), UserStatus.normal.getValue());
        Assert.assertEquals(userVo.getSex(), saveUserDto.getSex());
        Assert.assertEquals(userVo.getType(), saveUserDto.getType());
        Assert.assertEquals(userVo.getAvatar(), saveUserDto.getAvatar());
        Assert.assertEquals(userVo.getEmail(), saveUserDto.getEmail());
        Assert.assertEquals(userVo.getNickName(), saveUserDto.getNickName());
        Assert.assertEquals(userVo.getPhone(), saveUserDto.getPhone());
        Assert.assertEquals(userVo.getUserName(), saveUserDto.getUserName());
        Assert.assertEquals(userVo.getRoleList().size(), saveUserDto.getRoleIdList().size());

        Assert.assertTrue(passwordEncoder.matches(saveUserDto.getPassword(), userVo.getPassword()));
    }

    @Test
    public void success2updateAdminUser() throws Exception {

        SaveRoleDto saveRoleDto = new SaveRoleDto();
        saveRoleDto.setName("超级管理员" + UUID.randomUUID());
        saveRoleDto.setSort(1);
        Response<RoleVo> response = super.saveRole(saveRoleDto);
        Assert.assertNotNull(response);
        RoleVo roleVo;
        Assert.assertNotNull(roleVo = response.getData());

        SaveUserDto saveUserDto = new SaveUserDto();
        saveUserDto.setPassword("123456");
        saveUserDto.setAvatar("https://www.baidu.com/");
        saveUserDto.setEmail("56456554@qq,com");
        saveUserDto.setNickName("阿里巴巴");
        saveUserDto.setPhone("15147477745");
        saveUserDto.setSex(UserSex.boy.getValue());
        saveUserDto.setUserName("alibaba" + UUID.randomUUID());
        saveUserDto.setRoleIdList(Lists.newArrayList(roleVo.getId()));

        Response<UserVo> addResponse = super.saveAdminUser(saveUserDto);
        Assert.assertNotNull(addResponse);
        UserVo addedUserVo;
        Assert.assertNotNull(addedUserVo = addResponse.getData());

        // 待修改的角色
        saveRoleDto = new SaveRoleDto();
        saveRoleDto.setName("管理员" + UUID.randomUUID());
        saveRoleDto.setSort(1);
        response = super.saveRole(saveRoleDto);
        Assert.assertNotNull(response);
        Assert.assertNotNull(roleVo = response.getData());

        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setId(addedUserVo.getId());
        updateUserDto.setNickName(addedUserVo.getNickName() + "update");
        updateUserDto.setAvatar("https://www.json.cn/");
        updateUserDto.setEmail("12121212@qq.com");
        updateUserDto.setPhone("15145874562");
        updateUserDto.setSex(UserSex.girl.getValue());
        updateUserDto.setRoleIdList(Lists.newArrayList(roleVo.getId()));

        Response<UserVo> updateResponse = super.updateAdminUser(updateUserDto);
        UserVo updatedUser;
        Assert.assertNotNull(updatedUser = updateResponse.getData());

        // 属性未修改
        Assert.assertEquals(updatedUser.getStatus(), addedUserVo.getStatus());
        Assert.assertEquals(updatedUser.getType(), addedUserVo.getType());
        Assert.assertEquals(updatedUser.getUserName(), addedUserVo.getUserName());
        // 属性修改
        Assert.assertEquals(updatedUser.getSex(), updateUserDto.getSex());
        Assert.assertEquals(updatedUser.getAvatar(), updateUserDto.getAvatar());
        Assert.assertEquals(updatedUser.getEmail(), updateUserDto.getEmail());
        Assert.assertEquals(updatedUser.getNickName(), updateUserDto.getNickName());
        Assert.assertEquals(updatedUser.getPhone(), updateUserDto.getPhone());
        Assert.assertEquals(updatedUser.getRoleList().size(), updateUserDto.getRoleIdList().size());

        // 密码未修改
        Assert.assertTrue(passwordEncoder.matches(saveUserDto.getPassword(), updatedUser.getPassword()));
    }

    /**
     * 成功重置用户密码
     *
     * @throws Exception
     */
    @Test
    public void success2resetUserPwd() throws Exception {

        SaveRoleDto saveRoleDto = new SaveRoleDto();
        saveRoleDto.setName("超级管理员" + UUID.randomUUID());
        saveRoleDto.setSort(1);
        Response<RoleVo> response = super.saveRole(saveRoleDto);
        Assert.assertNotNull(response);
        RoleVo roleVo;
        Assert.assertNotNull(roleVo = response.getData());

        SaveUserDto saveUserDto = new SaveUserDto();
        saveUserDto.setPassword("123456");
        saveUserDto.setAvatar("https://www.baidu.com/");
        saveUserDto.setEmail("56456554@qq,com");
        saveUserDto.setNickName("阿里巴巴");
        saveUserDto.setPhone("15147477745");
        saveUserDto.setSex(UserSex.boy.getValue());
        saveUserDto.setUserName("alibaba" + UUID.randomUUID());
        saveUserDto.setRoleIdList(Lists.newArrayList(roleVo.getId()));

        Response<UserVo> addResponse = super.saveAdminUser(saveUserDto);
        Assert.assertNotNull(addResponse);
        UserVo addedUserVo;
        Assert.assertNotNull(addedUserVo = addResponse.getData());

        // 修改密码
        ResetUserPwdDto resetUserPwdDto = new ResetUserPwdDto();
        resetUserPwdDto.setId(addedUserVo.getId());
        resetUserPwdDto.setNewPwd("888888");

        Response<UserVo> updateResponse = super.resetAdminUserPwd(resetUserPwdDto);
        UserVo updatedUser;
        Assert.assertNotNull(updatedUser = updateResponse.getData());

        Assert.assertTrue(passwordEncoder.matches(resetUserPwdDto.getNewPwd(), updatedUser.getPassword()));
    }

    /**
     * 成功删除用户
     *
     * @throws Exception
     */
    @Test
    public void success2deleteUser() throws Exception {

        SaveRoleDto saveRoleDto = new SaveRoleDto();
        saveRoleDto.setName("超级管理员" + UUID.randomUUID());
        saveRoleDto.setSort(1);
        Response<RoleVo> response = super.saveRole(saveRoleDto);
        Assert.assertNotNull(response);
        RoleVo roleVo;
        Assert.assertNotNull(roleVo = response.getData());

        SaveUserDto saveUserDto = new SaveUserDto();
        saveUserDto.setPassword("123456");
        saveUserDto.setAvatar("https://www.baidu.com/");
        saveUserDto.setEmail("56456554@qq,com");
        saveUserDto.setNickName("阿里巴巴");
        saveUserDto.setPhone("15147477745");
        saveUserDto.setSex(UserSex.boy.getValue());
        saveUserDto.setUserName("alibaba" + UUID.randomUUID());
        saveUserDto.setRoleIdList(Lists.newArrayList(roleVo.getId()));

        Response<UserVo> addResponse = super.saveAdminUser(saveUserDto);
        Assert.assertNotNull(addResponse);
        UserVo addedUserVo;
        Assert.assertNotNull(addedUserVo = addResponse.getData());

        // 删除用户
        Response<Boolean> deleteResponse = super.deleteUser(addedUserVo.getId());
        Assert.assertTrue(deleteResponse.getData());
    }
}
