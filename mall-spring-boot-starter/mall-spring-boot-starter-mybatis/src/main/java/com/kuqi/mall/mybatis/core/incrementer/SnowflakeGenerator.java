package com.kuqi.mall.mybatis.core.incrementer;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 雪花算法id生成器
 *
 * @Author iloveoverfly
 * @Date 2021/1/25 18:37
 **/
public class SnowflakeGenerator implements IdentifierGenerator {

    private static final Snowflake snowflake = IdUtil
            .createSnowflake(ThreadLocalRandom.current().nextLong(30), ThreadLocalRandom.current().nextLong(30));

    @Override
    public Number nextId(Object entity) {
        return snowflake.nextId();
    }
}
