package com.kuqi.mall.system.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 15:50
 **/
public enum MenuMethod implements IEnum<Integer> {
    /**
     * url请求方法
     */
    get(1, "get"),
    put(2, "put"),
    post(3, "post"),
    delete(4, "delete");

    private final Integer value;
    private final String desc;

    MenuMethod(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public static MenuMethod valueOf(Integer value) {
        
        for (MenuMethod menuMethod : MenuMethod.values()) {
            if (menuMethod.getValue().equals(value)) {
                return menuMethod;
            }
        }
        return null;
    }
}
