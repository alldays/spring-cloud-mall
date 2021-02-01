package com.kuqi.mall.system.core.converter;

import com.google.common.collect.Lists;
import com.kuqi.mall.system.entity.dto.SaveUserDto;
import com.kuqi.mall.system.entity.dto.UpdateUserDto;
import com.kuqi.mall.system.entity.po.User;
import com.kuqi.mall.system.entity.po.UserRole;
import com.kuqi.mall.system.entity.vo.UserVo;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 10:10
 **/
@Mapper(componentModel = "spring")
public interface UserConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    User fromSaveUserDto(SaveUserDto saveUserDto);

    User fromUpdateUserDto(UpdateUserDto updateUserDto);

    UserVo toUserVo(User user);

    default List<UserRole> convert2UserRoleList(Long userId, List<Long> roleIdList) {

        if (Objects.isNull(userId) || CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        return roleIdList.stream()
                .filter(Objects::nonNull)
                .map(roleId -> UserRole.builder()
                        .roleId(roleId)
                        .userId(userId)
                        .build())
                .collect(Collectors.toList());
    }
}
