package com.kuqi.mall.system.entity.bo;

import com.kuqi.mall.system.entity.enums.MenuType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Author iloveoverfly
 * @Date 2021/1/29 18:02
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuQuery implements Serializable {

    private static final long serialVersionUID = 8473809730128089150L;
    private String name;
    /**
     * 类型（1:目录2:菜单3:按钮4:url）
     * {@link MenuType}
     */
    private Integer type;

    private Collection<Long> ids;
}
