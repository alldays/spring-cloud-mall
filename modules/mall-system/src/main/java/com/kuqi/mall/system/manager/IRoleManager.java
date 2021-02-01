package com.kuqi.mall.system.manager;

import com.kuqi.mall.system.entity.dto.SaveRoleDto;
import com.kuqi.mall.system.entity.dto.UpdateRoleDto;
import com.kuqi.mall.system.entity.vo.RoleVo;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 20:32
 **/
public interface IRoleManager {

    RoleVo save(SaveRoleDto saveRoleDto);

    RoleVo update(UpdateRoleDto updateRoleDto);

    Boolean delete(Long id);

    RoleVo getRoleVo(Long id);
}
