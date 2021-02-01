package com.kuqi.mall.cache.core;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author iloveoverfly
 * @Date 2020/2/26 8:04
 **/
public interface RedisConfig {

    /**
     * 名称与失效时间配置的分隔符
     * users#10_SECONDS
     */
    String NAME_TIME_CONFIG_SEPARATOR = "#";

    /**
     * 失效时间与单位的分隔符
     * 10_SECONDS
     * {@link TimeUnit}
     */
    String TTL_TIME_SEPARATOR = "_";

    /**
     * 失效时间随机器
     */
    Random EXPIRE_RANDOM = new Random();

    /**
     * 默认锁时间限制
     */
    long DEFAULT_LOCK_EXPIRED_TIME = 300000L;

    /**
     * 默认尝试获取锁的时间
     */
    long DEFAULT_TRY_LOCK_TIMEOUT = 1000L;

    /**
     * 默认的数据缓存失效时间  // 24小时，单位为
     */
    long DEFAULT_CACHED_TIME_OUT = 24L;
}
