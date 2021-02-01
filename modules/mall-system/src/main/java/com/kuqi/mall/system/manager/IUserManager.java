package com.kuqi.mall.system.manager;

import com.kuqi.mall.system.entity.dto.ResetUserPwdDto;
import com.kuqi.mall.system.entity.dto.SaveUserDto;
import com.kuqi.mall.system.entity.dto.UpdateUserDto;
import com.kuqi.mall.system.entity.vo.UserVo;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 10:05
 **/
public interface IUserManager extends UserDetailsService {

    UserVo save(SaveUserDto saveUserDto);

    UserVo update(UpdateUserDto updateUserDto);

    UserVo getUserVo(Long id);

    UserVo resetPwd(ResetUserPwdDto resetUserPwdDto);

    Boolean delete(Long id);
}
