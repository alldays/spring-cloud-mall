package com.kuqi.mall.mybatis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.kuqi.mall.mybatis.core.handler.BaseEntityHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author iloveoverfly
 * @Date 2021/1/25 18:28
 **/
@Configuration
@MapperScan("com.kuqi.mall.*.dao")
public class MybatisPlusConfig {

    /**
     * mybatis plus插件
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {

        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 分页插件
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 乐观锁插件
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return mybatisPlusInterceptor;
    }

    /**
     * baseEntity 自动填充
     */
    @Bean
    public BaseEntityHandler baseEntityHandler() {
        return new BaseEntityHandler();
    }
}
