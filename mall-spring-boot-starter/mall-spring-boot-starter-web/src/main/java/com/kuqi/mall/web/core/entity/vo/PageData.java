package com.kuqi.mall.web.core.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 16:07
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageData<T> implements Serializable {

    private static final long serialVersionUID = 3648973434736595666L;

    private long total;
    private List<T> data;
}
