package com.kuqi.mall.system.manager.impl;

import com.kuqi.maill.common.core.lock.OptimisticRetry;
import com.kuqi.mall.security.core.entity.LoginUser;
import com.kuqi.mall.security.core.utils.LoginUserUtils;
import com.kuqi.mall.system.core.converter.MenuConverter;
import com.kuqi.mall.system.entity.bo.MenuQuery;
import com.kuqi.mall.system.entity.bo.RoleMenuQuery;
import com.kuqi.mall.system.entity.bo.UserRoleQuery;
import com.kuqi.mall.system.entity.dto.SaveMenuDto;
import com.kuqi.mall.system.entity.dto.UpdateMenuDto;
import com.kuqi.mall.system.entity.enums.MenuStatus;
import com.kuqi.mall.system.entity.enums.MenuVisible;
import com.kuqi.mall.system.entity.po.Menu;
import com.kuqi.mall.system.entity.po.RoleMenu;
import com.kuqi.mall.system.entity.po.UserRole;
import com.kuqi.mall.system.entity.query.MenuListQuery;
import com.kuqi.mall.system.entity.vo.MenuVo;
import com.kuqi.mall.system.manager.IMenuManager;
import com.kuqi.mall.system.service.IMenuService;
import com.kuqi.mall.system.service.IRoleMenuService;
import com.kuqi.mall.system.service.IUserRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kuqi.mall.system.entity.constants.Constants.MENU_LOCK;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 21:44
 **/
@Service
public class MenuManagerImpl implements IMenuManager {

    @Autowired
    private IMenuService menuService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleMenuService roleMenuService;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public List<MenuVo> list(MenuListQuery menuListQuery) {

        LoginUser user = LoginUserUtils.getLoginUser();
        MenuQuery menuQuery = MenuConverter.INSTANCE.toMenuQuery(menuListQuery);

        if (!user.getIsAdmin()) {

            List<UserRole> userRoleList = userRoleService.list(UserRoleQuery.builder().userId(user.getId()).build());
            if (CollectionUtils.isNotEmpty(userRoleList)) {

                Set<Long> roleIds = userRoleList.stream()
                        .map(UserRole::getRoleId).filter(Objects::nonNull)
                        .collect(Collectors.toSet());
                List<RoleMenu> roleMenuList = this.roleMenuService.list(RoleMenuQuery.builder().roleIds(roleIds).build());
                if (CollectionUtils.isNotEmpty(roleMenuList)) {

                    Set<Long> menuIds = roleMenuList.stream()
                            .map(RoleMenu::getMenuId).filter(Objects::nonNull)
                            .collect(Collectors.toSet());
                    menuQuery.setIds(menuIds);
                }
            }
        }
        List<Menu> menuList = this.menuService.list(menuQuery);
        if (CollectionUtils.isEmpty(menuList)) {
            return null;
        }
        return MenuConverter.INSTANCE.toMenuVo(menuList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MenuVo save(SaveMenuDto saveMenuDto) {

        LoginUser loginUser = LoginUserUtils.getLoginUser();
        String lockKey = MENU_LOCK + loginUser.getUserName();
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        try {

            Menu addingMenu = MenuConverter.INSTANCE.fromSaveMenuDto(saveMenuDto);
            addingMenu.setVisible(MenuVisible.show.getValue());
            addingMenu.setStatus(MenuStatus.normal.getValue());
            menuService.save(addingMenu);
            Menu menu = menuService.getById(addingMenu.getId());
            return MenuConverter.INSTANCE.toMenuVo(menu);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @OptimisticRetry
    @Transactional(rollbackFor = Exception.class)
    public MenuVo update(UpdateMenuDto updateMenuDto) {

        Long menuId = updateMenuDto.getId();
        Menu existingMenu = this.menuService.getById(menuId);
        Menu updatingMenu = MenuConverter.INSTANCE.fromUpdateMenuDto(updateMenuDto);
        updatingMenu.setVersion(existingMenu.getVersion());
        if (!this.menuService.updateById(updatingMenu)) {
            throw new OptimisticLockingFailureException("修改菜单失败！");
        }
        Menu menu = menuService.getById(menuId);
        return MenuConverter.INSTANCE.toMenuVo(menu);
    }

    @Override
    @OptimisticRetry
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {

        if (Objects.isNull(id)) {
            return false;
        }
        Menu existingMenu = this.menuService.getById(id);
        if (Objects.isNull(existingMenu)) {
            return false;
        }
        if (!this.menuService.removeById(id)) {
            throw new OptimisticLockingFailureException("删除菜单失败！");
        }
        return true;
    }
}
