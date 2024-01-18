package com.yanshen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = RedisAutoConfiguration.class)
@EnableFeignClients
@EnableDiscoveryClient
public class MqListenService {

    public static void main(String[] args) {
        SpringApplication.run(MqListenService.class, args);
    }

}
