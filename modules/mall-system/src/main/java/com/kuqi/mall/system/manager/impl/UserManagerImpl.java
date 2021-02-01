package com.kuqi.mall.system.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.kuqi.maill.common.core.lock.OptimisticRetry;
import com.kuqi.mall.system.core.converter.MenuConverter;
import com.kuqi.mall.system.core.converter.RoleConverter;
import com.kuqi.mall.system.core.converter.UserConverter;
import com.kuqi.mall.system.entity.bo.RoleMenuQuery;
import com.kuqi.mall.system.entity.bo.UserQuery;
import com.kuqi.mall.system.entity.bo.UserRoleQuery;
import com.kuqi.mall.system.entity.dto.ResetUserPwdDto;
import com.kuqi.mall.system.entity.dto.SaveUserDto;
import com.kuqi.mall.system.entity.dto.UpdateUserDto;
import com.kuqi.mall.system.entity.enums.UserStatus;
import com.kuqi.mall.system.entity.po.*;
import com.kuqi.mall.system.entity.vo.MenuVo;
import com.kuqi.mall.system.entity.vo.UserVo;
import com.kuqi.mall.system.manager.IUserManager;
import com.kuqi.mall.system.service.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kuqi.mall.system.entity.constants.ExceptionConstant.USER_NEW_PWD_IS_EXIST;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 10:05
 **/
@Service
public class UserManagerImpl implements IUserManager {

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRoleMenuService roleMenuService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value(value = "${user.default.password:123456}")
    private String defaultPassword;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVo save(SaveUserDto saveUserDto) {

        User addingUser = UserConverter.INSTANCE.fromSaveUserDto(saveUserDto);
        addingUser.setStatus(UserStatus.normal.getValue());
        // 加密密码
        if (StringUtils.isBlank(addingUser.getPassword())) {
            addingUser.setPassword(defaultPassword);
        }
        addingUser.setPassword(passwordEncoder.encode(addingUser.getPassword()));
        // 保存用户
        this.userService.save(addingUser);

        List<UserRole> userRoleList = UserConverter.INSTANCE.convert2UserRoleList(addingUser.getId(), saveUserDto.getRoleIdList());
        if (CollectionUtils.isNotEmpty(userRoleList)) {
            // 用户关联的角色
            userRoleService.saveBatch(userRoleList);
        }
        return this.getUserVo(addingUser.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OptimisticRetry
    public UserVo update(UpdateUserDto updateUserDto) {

        User updatingUser = UserConverter.INSTANCE.fromUpdateUserDto(updateUserDto);
        Long userId = updateUserDto.getId();
        User existingUser = this.userService.getById(userId);
        updatingUser.setVersion(existingUser.getVersion());
        if (!this.userService.updateById(updatingUser)) {
            throw new OptimisticLockingFailureException("修改用户失败！");
        }
        List<Long> roleIdList;
        if (CollectionUtils.isNotEmpty(roleIdList = updateUserDto.getRoleIdList())) {

            // 清理历史数据
            QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            this.userRoleService.remove(queryWrapper);

            List<UserRole> userRoleList = UserConverter.INSTANCE.convert2UserRoleList(userId, roleIdList);
            if (CollectionUtils.isNotEmpty(userRoleList)) {
                // 用户关联的角色
                userRoleService.saveBatch(userRoleList);
            }
        }
        return this.getUserVo(userId);
    }

    @Override
    public UserVo getUserVo(Long id) {

        if (Objects.isNull(id)) {
            return null;
        }
        User existingUser = this.userService.getById(id);
        if (Objects.isNull(existingUser)) {
            return null;
        }
        UserVo userVo = UserConverter.INSTANCE.toUserVo(existingUser);
        // 追加权限与角色
        this.appendUserVo(userVo);
        return userVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OptimisticRetry
    public UserVo resetPwd(ResetUserPwdDto resetUserPwdDto) {

        Long userId = resetUserPwdDto.getId();
        User existingUser = this.userService.getById(userId);
        String newPwd;
        if (this.passwordEncoder.matches(newPwd = resetUserPwdDto.getNewPwd(), existingUser.getPassword())) {
            throw new IllegalArgumentException(USER_NEW_PWD_IS_EXIST);
        }
        User updatingUser = new User();
        updatingUser.setId(userId);
        updatingUser.setPassword(this.passwordEncoder.encode(newPwd));
        if (!this.userService.updateById(updatingUser)) {
            throw new OptimisticLockingFailureException("修改用户密码失败！");
        }
        User user = this.userService.getById(userId);
        return UserConverter.INSTANCE.toUserVo(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OptimisticRetry
    public Boolean delete(Long id) {

        if (Objects.isNull(id)
                || Objects.isNull(this.userService.getById(id))) {
            return false;
        }
        if (!this.userService.removeById(id)) {
            throw new OptimisticLockingFailureException("删除用户失败！");
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isBlank(username)) {
            return null;
        }
        User existingUser = this.userService.get(UserQuery.builder().userName(username).build());
        if (Objects.isNull(existingUser)) {
            return null;
        }
        UserVo userVo = UserConverter.INSTANCE.toUserVo(existingUser);
        // 追加权限与角色
        this.appendUserVo(userVo);
        return userVo;
    }

    private void appendUserVo(UserVo userVo) {

        if (userVo.getIsAdmin()) {

            MenuVo menuVo = new MenuVo();
            menuVo.setPerms("admin");
            userVo.setMenuList(Lists.newArrayList(menuVo));
            return;
        }
        // 非超级管理员
        List<UserRole> userRoleList;
        List<Long> roleIdList;
        List<Role> roleList;
        if (CollectionUtils.isNotEmpty(userRoleList = this.userRoleService.list(UserRoleQuery.builder().userId(userVo.getId()).build()))
                && CollectionUtils.isNotEmpty(roleIdList = userRoleList.stream()
                .filter(userRole -> Objects.nonNull(userRole) && Objects.nonNull(userRole.getRoleId()))
                .map(UserRole::getRoleId).collect(Collectors.toList()))
                && CollectionUtils.isNotEmpty(roleList = this.roleService.listByIds(roleIdList))) {

            userVo.setRoleList(RoleConverter.INSTANCE.toRoleVo(roleList));

            List<RoleMenu> roleMenuList;
            Set<Long> menuIds;
            List<Menu> menuList;
            if (CollectionUtils.isNotEmpty(roleMenuList = this.roleMenuService.list(RoleMenuQuery.builder()
                    .roleIds(Sets.newHashSet(roleIdList)).build()))
                    && CollectionUtils.isNotEmpty(menuIds = roleMenuList.stream()
                    .filter(roleMenu -> Objects.nonNull(roleMenu) && Objects.nonNull(roleMenu.getMenuId()))
                    .map(RoleMenu::getMenuId).collect(Collectors.toSet()))
                    && CollectionUtils.isNotEmpty(menuList = this.menuService.listByIds(menuIds))) {

                userVo.setMenuList(MenuConverter.INSTANCE.toMenuVo(menuList));
            }
        }
    }
}
