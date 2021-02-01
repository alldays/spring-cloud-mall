package com.kuqi.mall.system.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 22:05
 **/
public enum UserType implements IEnum<Integer> {

    /**
     * 用户类型
     */
    admin(0, "管理员"),
    consumer(1, "普通用户");

    private final Integer value;
    private final String desc;

    UserType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
