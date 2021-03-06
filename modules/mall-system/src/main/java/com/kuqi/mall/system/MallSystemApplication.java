package com.kuqi.mall.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 20:59
 **/
@EnableDiscoveryClient
@SpringBootApplication
public class MallSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallSystemApplication.class, args);
    }
}
