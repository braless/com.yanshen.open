package com.yanshen.auth;

import com.yanshen.common.security.annotation.CustomConfig;
import com.yanshen.common.security.annotation.CustomFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

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
}