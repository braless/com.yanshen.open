package com.yanshen.auth;

import com.yanshen.common.security.annotation.CustomConfig;
import com.yanshen.common.security.annotation.CustomFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-18 13:25
 * @Description:
 * @Location: com.yanshen
 * @Project: Default (Template) Project
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@CustomConfig
@CustomFeignClients
public class AuthService {
    public static void main(String[] args) {
        SpringApplication.run(AuthService.class, args);
        System.out.println("Hello world!");
    }
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}