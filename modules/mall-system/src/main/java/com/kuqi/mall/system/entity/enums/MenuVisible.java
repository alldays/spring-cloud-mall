package com.kuqi.mall.system.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 11:18
 **/
public enum MenuVisible implements IEnum<Integer> {

    /**
     * 菜单状态
     */
    show(0, "显示"),
    hide(1, "隐藏");

    private final Integer value;
    private final String desc;

    MenuVisible(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public static MenuVisible valueOf(Integer value) {

        for (MenuVisible e : MenuVisible.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }
}
