package com.kuqi.mall.system.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuqi.maill.common.core.lock.OptimisticRetry;
import com.kuqi.mall.system.core.converter.MenuConverter;
import com.kuqi.mall.system.core.converter.RoleConverter;
import com.kuqi.mall.system.entity.dto.SaveRoleDto;
import com.kuqi.mall.system.entity.dto.UpdateRoleDto;
import com.kuqi.mall.system.entity.enums.RoleStatus;
import com.kuqi.mall.system.entity.po.Menu;
import com.kuqi.mall.system.entity.po.Role;
import com.kuqi.mall.system.entity.po.RoleMenu;
import com.kuqi.mall.system.entity.bo.RoleMenuQuery;
import com.kuqi.mall.system.entity.vo.RoleVo;
import com.kuqi.mall.system.manager.IRoleManager;
import com.kuqi.mall.system.service.IMenuService;
import com.kuqi.mall.system.service.IRoleMenuService;
import com.kuqi.mall.system.service.IRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 20:32
 **/
@Service
public class RoleManagerImpl implements IRoleManager {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRoleMenuService roleMenuService;
    @Autowired
    private IMenuService menuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleVo save(SaveRoleDto saveRoleDto) {

        Role addingRole = RoleConverter.INSTANCE.fromSaveRoleDto(saveRoleDto);
        addingRole.setStatus(RoleStatus.normal.getValue());
        this.roleService.save(addingRole);

        List<Long> menuIdList;
        List<RoleMenu> roleMenuList;
        if (CollectionUtils.isNotEmpty(menuIdList = saveRoleDto.getMenuIdList())
                && CollectionUtils.isNotEmpty(roleMenuList = RoleConverter.INSTANCE.convert2RoleMenu(addingRole.getId(), menuIdList))) {
            this.roleMenuService.saveBatch(roleMenuList);
        }
        return this.getRoleVo(addingRole.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OptimisticRetry
    public RoleVo update(UpdateRoleDto updateRoleDto) {

        Role updatingRole = RoleConverter.INSTANCE.fromUpdateRoleDto(updateRoleDto);
        Long id = updateRoleDto.getId();
        Role existingRole = this.roleService.getById(id);
        updatingRole.setVersion(existingRole.getVersion());
        if (!this.roleService.updateById(updatingRole)) {
            throw new OptimisticLockingFailureException("修改角色失败！");
        }

        List<Long> menuIdList;
        if (CollectionUtils.isNotEmpty(menuIdList = updateRoleDto.getMenuIdList())) {

            QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", id);
            this.roleMenuService.remove(queryWrapper);

            List<RoleMenu> roleMenuList;
            if (CollectionUtils.isNotEmpty(roleMenuList = RoleConverter.INSTANCE.convert2RoleMenu(id, menuIdList))) {
                this.roleMenuService.saveBatch(roleMenuList);
            }
        }
        return this.getRoleVo(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OptimisticRetry
    public Boolean delete(Long id) {

        if (Objects.isNull(id)) {
            return false;
        }
        Role existingRole = this.roleService.getById(id);
        if (Objects.isNull(existingRole)) {
            return false;
        }
        if (!this.roleService.removeById(existingRole)) {
            throw new OptimisticLockingFailureException("删除角色失败！");
        }
        return true;
    }

    @Override
    public RoleVo getRoleVo(Long id) {

        Role role;
        if (Objects.isNull(id)
                || Objects.isNull(role = roleService.getById(id))) {
            return null;
        }
        RoleVo roleVo = RoleConverter.INSTANCE.toRoleVo(role);
        List<RoleMenu> roleMenuList;
        Set<Long> menuIds;
        List<Menu> menuList;
        if (CollectionUtils.isNotEmpty(roleMenuList = roleMenuService.list(RoleMenuQuery.builder().roleId(id).build()))
                && CollectionUtils.isNotEmpty(menuIds = roleMenuList.stream()
                .filter(roleMenu -> Objects.nonNull(roleMenu) && Objects.nonNull(roleMenu.getMenuId()))
                .map(RoleMenu::getMenuId).collect(Collectors.toSet()))
                && CollectionUtils.isNotEmpty(menuList = this.menuService.listByIds(menuIds))) {

            roleVo.setMenuList(MenuConverter.INSTANCE.toMenuVo(menuList));
        }
        return roleVo;
    }
}
