package com.kuqi.mall.system.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @Author iloveoverfly
 * @Date 2021/1/28 22:08
 **/
public enum UserSex implements IEnum<Integer> {

    /**
     * 用户性别
     */
    boy(1, "男生"),
    girl(2, "女生"),
    unknown(3, "未知");

    private final Integer value;
    private final String desc;

    UserSex(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static UserSex valueOf(Integer value) {

        for (UserSex e : UserSex.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
