package com.kuqi.mall.redis.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.SentinelServersConfig;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 使用redisson配置
 *
 * @Author iloveoverfly
 * @Date 2021/1/26 22:22
 **/
@Configuration
@EnableConfigurationProperties({RedisProperties.class})
public class RedisConfig {

    private final static String SSL_REDIS = "redis://";

    @Autowired
    private RedisProperties redisProperties;

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

    /**
     * 使用RedissonConnectionFactory
     */
    @Bean
    public RedissonConnectionFactory redissonConnectionFactory() {
        return new RedissonConnectionFactory(redissonClient());
    }

    /**
     * redisson 客户端配置
     */
    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean({RedisProperties.class})
    public org.redisson.api.RedissonClient redissonClient() {

        Config config = new Config();

        RedisProperties.Sentinel sentinel;
        RedisProperties.Cluster cluster;
        // 哨兵模式
        if (Objects.nonNull(sentinel = this.redisProperties.getSentinel())) {

            List<String> newNodes = sentinel.getNodes()
                    .stream().map((index) -> index.startsWith(SSL_REDIS) ? index : SSL_REDIS + index)
                    .collect(Collectors.toList());

            SentinelServersConfig serverConfig = config.useSentinelServers()
                    .addSentinelAddress(newNodes.toArray(new String[0]))
                    .setPassword(this.redisProperties.getPassword())
                    .setMasterName(sentinel.getMaster())
                    .setReadMode(ReadMode.SLAVE);
            serverConfig.setDatabase(redisProperties.getDatabase());
        }
        // 集群模式
        else if (Objects.nonNull(cluster = this.redisProperties.getCluster())) {

            config.useClusterServers().addNodeAddress(cluster.getNodes().toArray(new String[0]));
        }
        // 单机模式
        else {

            String address = this.redisProperties.getHost() + ":" + this.redisProperties.getPort();
            config.useSingleServer().setAddress(address);
        }
        return Redisson.create(config);
    }
}
