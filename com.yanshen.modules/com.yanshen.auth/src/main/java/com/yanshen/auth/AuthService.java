package com.yanshen.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-18 13:25
 * @Description:
 * @Location: com.yanshen
 * @Project: Default (Template) Project
 */
@SpringBootApplication
public class AuthService {
    public static void main(String[] args) {
        SpringApplication.run(AuthService.class, args);
        System.out.println("Hello world!");
    }
}