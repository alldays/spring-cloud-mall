package com.kuqi.mall.system.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 20:20
 **/
public enum RoleStatus implements IEnum<Integer> {

    /**
     * 菜单状态
     */
    normal(0, "正常"),
    stop(1, "停用");

    private final Integer value;
    private final String desc;

    RoleStatus(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
