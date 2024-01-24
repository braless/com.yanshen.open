package com.yanshen.messager;

import com.yanshen.common.security.annotation.CustomConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-19 10:52
 * @Description:
 * @Location: PACKAGE_NAME
 * @Project: com.yanshen.open
 */
@SpringBootApplication
@CustomConfig
@EnableFeignClients
@EnableOpenApi
public class MessageServive {

    public static void main(String[] args) {
        SpringApplication.run(MessageServive.class, args);
    }
}
