package com.kuqi.mall.system.core.converter;

import com.google.common.collect.Lists;
import com.kuqi.mall.system.entity.dto.SaveRoleDto;
import com.kuqi.mall.system.entity.dto.UpdateRoleDto;
import com.kuqi.mall.system.entity.po.Role;
import com.kuqi.mall.system.entity.po.RoleMenu;
import com.kuqi.mall.system.entity.vo.RoleVo;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 20:33
 **/
@Mapper(componentModel = "spring")
public interface RoleConverter {

    RoleConverter INSTANCE = Mappers.getMapper(RoleConverter.class);

    Role fromSaveRoleDto(SaveRoleDto saveRoleDto);

    Role fromUpdateRoleDto(UpdateRoleDto updateRoleDto);

    RoleVo toRoleVo(Role role);

    List<RoleVo> toRoleVo(List<Role> roleList);

    default List<RoleMenu> convert2RoleMenu(Long roleId, List<Long> menuIdList) {

        if (Objects.isNull(roleId)
                || CollectionUtils.isEmpty(menuIdList)) {
            return Lists.newArrayList();
        }
        return menuIdList.stream()
                .filter(Objects::nonNull)
                .map(menuId -> RoleMenu.builder()
                        .roleId(roleId)
                        .menuId(menuId)
                        .build())
                .collect(Collectors.toList());
    }
}
