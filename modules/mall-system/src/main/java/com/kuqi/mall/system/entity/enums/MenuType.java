package com.kuqi.mall.system.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 11:14
 **/
public enum MenuType implements IEnum<Integer> {

    /**
     * 菜单类型
     */
    contents(1, "目录"),
    menu(2, "菜单"),
    button(3, "按钮"),
    url(4, "url");

    private final Integer value;
    private final String desc;

    MenuType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    @Override
    public Integer getValue() {
        return value;
    }

    public static MenuType valueOf(Integer value) {

        for (MenuType e : MenuType.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

}
