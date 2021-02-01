package com.kuqi.mall.cache.config;

import com.kuqi.mall.cache.core.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * @Author iloveoverfly
 * @Date 2021/1/26 22:00
 **/
@Configuration
@EnableCaching
public class CacheConfig {

    @Autowired
    private RedisSerializer<Object> redisFastJsonSerializer;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 配置 spring cache manager RedisCacheManager
     */
    @Bean
    @ConditionalOnMissingBean(value = CacheManager.class)
    public CacheManager cacheManager() {

        // redis 缓存配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisFastJsonSerializer));
        RedisCacheManager cacheManager = new ExpireTimeRedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory), config);
        return cacheManager;
    }

    /**
     * 可以自动设置失效时间的RedisCacheManager
     * 失效时间设置规则 @Cacheable(cacheNames = "users#10_SECONDS")
     * # 为cacheNames 与自定义失效时间的分隔符
     * _ 为失效时间值与单位的分隔符
     *
     * @Author iloveoverfly
     * @LocalDateTime 2020/2/13 19:26
     **/
    @Slf4j
    private static class ExpireTimeRedisCacheManager extends RedisCacheManager {

        private ExpireTimeRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
            super(cacheWriter, defaultCacheConfiguration);
        }

        /**
         * 生成缓存配置
         *
         * @param name        设置的缓存名称（完整配置的例子users#10_SECONDS）
         * @param cacheConfig redis缓存配置
         * @return
         */
        @Override
        protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {

            if (StringUtils.isEmpty(name)) {
                return super.createRedisCache(name, cacheConfig);
            }
            String[] nameSplits = name.split(RedisConfig.NAME_TIME_CONFIG_SEPARATOR);
            // 默认的失效时间设置
            if (this.isExpireTimeDefaultConfig(nameSplits)) {

                long minutes = cacheConfig.getTtl().toMinutes();
                if (log.isDebugEnabled()) {

                    log.debug("set default ttl minutes {}", minutes);
                }
                // 修改默认的过期时间，设置失效时间为默认的秒数，加上随机的生成的秒数
                long appendRandomMinutes = this.appendRandom(minutes);
                RedisCacheConfiguration appendRandomSecsRedisCacheConfiguration = cacheConfig.entryTtl(Duration.ofMinutes(appendRandomMinutes));
                return super.createRedisCache(name, appendRandomSecsRedisCacheConfiguration);

            } else {

                // 用户自定义失效时间
                String finalName = nameSplits[0];
                String ttlTimeConfig = nameSplits[1];
                String[] ttlTimeConfigSplits = ttlTimeConfig.split(RedisConfig.TTL_TIME_SEPARATOR);
                if (this.isCustomExpireTimeConfig(ttlTimeConfigSplits)) {

                    try {

                        Long time = Long.parseLong(ttlTimeConfigSplits[0]);
                        String timeUnitName = ttlTimeConfigSplits[1].toUpperCase();
                        TimeUnit timeUnit = TimeUnit.valueOf(timeUnitName);
                        Duration appendRandomSecsDuration = obtainAndAddRandomSecs(time, timeUnit);
                        RedisCacheConfiguration appendRandomSecsRedisCacheConfiguration = cacheConfig.entryTtl(appendRandomSecsDuration);
                        return super.createRedisCache(finalName, appendRandomSecsRedisCacheConfiguration);

                    } catch (Exception e) {

                        log.warn("obtain ttl time config error!", e);
                    }
                }
                return super.createRedisCache(finalName, cacheConfig);
            }
        }

        /**
         * 用户自定义失效时间配置
         */
        private boolean isCustomExpireTimeConfig(String[] ttlTimeConfigSplits) {
            return Objects.nonNull(ttlTimeConfigSplits) && 2 == ttlTimeConfigSplits.length;
        }

        /**
         * 默认失效时间的配置
         */
        private boolean isExpireTimeDefaultConfig(String[] nameSplits) {
            return Objects.nonNull(nameSplits) && 1 == nameSplits.length;
        }

        /**
         * 获取ttl失效时间并且随机增加毫秒数
         */
        private Duration obtainAndAddRandomSecs(Long time, TimeUnit timeUnit) {

            long seconds;
            switch (timeUnit) {
                case SECONDS:
                    seconds = time;
                    break;
                case MILLISECONDS:
                    seconds = TimeUnit.MILLISECONDS.toSeconds(time);
                    break;
                case HOURS:
                    seconds = TimeUnit.HOURS.toSeconds(time);
                    break;
                case MINUTES:
                    seconds = TimeUnit.MINUTES.toSeconds(time);
                    break;
                case DAYS:
                    seconds = TimeUnit.DAYS.toSeconds(time);
                    break;
                default:
                    seconds = TimeUnit.SECONDS.toSeconds(time);
                    break;
            }
            long secondsWithRandom = this.appendRandom(seconds);
            return Duration.ofSeconds(secondsWithRandom);
        }

        /**
         * 随机增加失效秒数，避免同一时间集体失效
         *
         * @param minutes 分钟数
         * @return
         */
        private long appendRandom(long minutes) {
            return minutes + RedisConfig.EXPIRE_RANDOM.nextInt(10);
        }
    }
}
