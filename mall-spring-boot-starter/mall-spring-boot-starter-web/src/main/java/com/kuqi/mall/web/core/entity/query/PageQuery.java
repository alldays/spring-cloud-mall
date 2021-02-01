package com.kuqi.mall.web.core.entity.query;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 16:09
 **/
@Data
public class PageQuery implements Serializable {
    private static final long serialVersionUID = -4644157590517114750L;
    /**
     * 当前页
     */
    protected Integer pageNum = 1;
    /**
     * 每页展示条数，默认20
     */
    protected Integer pageSize = 20;
}
