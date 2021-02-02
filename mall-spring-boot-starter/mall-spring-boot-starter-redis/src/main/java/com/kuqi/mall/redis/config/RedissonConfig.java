package com.kuqi.mall.redis.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.SentinelServersConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * redisson 哨兵模式客户端配置
 *
 * @Author iloveoverfly
 * @Date 2021/1/26 22:22
 **/
@Configuration
@EnableConfigurationProperties({RedisProperties.class})
public class RedissonConfig {

    @Autowired
    private RedisProperties redisProperties;

    /**
     * 哨兵模式 redisson 客户端
     */
    @Bean
    @ConditionalOnMissingBean({RedisProperties.class})
    public org.redisson.api.RedissonClient redissonClient() {

        Config config = new Config();

        RedisProperties.Sentinel sentinel;
        RedisProperties.Cluster cluster;
        // 哨兵模式
        if (Objects.nonNull(sentinel = this.redisProperties.getSentinel())) {

            SentinelServersConfig serverConfig = config.useSentinelServers()
                    .addSentinelAddress(sentinel.getNodes().toArray(new String[0]))
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
