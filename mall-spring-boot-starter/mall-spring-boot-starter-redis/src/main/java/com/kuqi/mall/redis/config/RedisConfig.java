package com.kuqi.mall.redis.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import io.lettuce.core.ReadFrom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * redis 自定义配置，开启spring cache缓存
 *
 * @Author iloveoverfly
 * @Date 2020/8/26 17:58
 **/
@Configuration
@Slf4j
@AutoConfigureBefore(RedisAutoConfiguration.class)
@EnableCaching
public class RedisConfig {

    /**
     * redis FastJson序列化
     */
    @Bean
    @ConditionalOnMissingBean(value = RedisSerializer.class)
    public RedisSerializer<Object> redisFastJsonSerializer() {
        return new GenericFastJsonRedisSerializer();
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(@Autowired RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    /**
     * 配置从库读取策略
     * 先从从库查询数据，如果失败则从master获取
     */
    @Bean
    public LettuceClientConfigurationBuilderCustomizer lettuceClientConfigurationBuilderCustomizer() {
        return clientConfigurationBuilder -> clientConfigurationBuilder.readFrom(ReadFrom.REPLICA_PREFERRED);
    }

    /**
     * 设置自定义序列化的RedisTemplate
     *
     * @param redisConnectionFactory
     * @param redisFastJsonSerializer 序列化
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(@Autowired RedisConnectionFactory redisConnectionFactory,
                                                       @Autowired RedisSerializer<Object> redisFastJsonSerializer) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // value，hash value设置FastJson序列化
        template.setHashValueSerializer(redisFastJsonSerializer);
        template.setValueSerializer(redisFastJsonSerializer);
        // key，hash key使用String序列化
        RedisSerializer<String> stringRedisSerializer = RedisSerializer.string();
        template.setHashKeySerializer(stringRedisSerializer);
        template.setKeySerializer(stringRedisSerializer);
        return template;
    }
}
