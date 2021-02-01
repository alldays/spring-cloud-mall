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

import java.util.List;

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
        List<String> nodes = this.redisProperties.getSentinel().getNodes();

        SentinelServersConfig serverConfig = config.useSentinelServers()
                .addSentinelAddress(nodes.toArray(new String[0]))
                .setPassword(this.redisProperties.getPassword())
                .setMasterName(this.redisProperties.getSentinel().getMaster())
                .setReadMode(ReadMode.SLAVE);

        serverConfig.setDatabase(redisProperties.getDatabase());
        return Redisson.create(config);
    }
}
