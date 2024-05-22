package com.yanshen.admin;

import com.yanshen.common.security.annotation.CustomConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @Author: Yanchao
 * @Date: 2024/5/21 下午4:38
 * @Version: v1.0.0
 * @Description: TODO
 **/
@SpringBootApplication
@CustomConfig
@EnableFeignClients
@EnableOpenApi
public class AdminService {
    public static void main(String[] args) {
        SpringApplication.run(AdminService.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  后台管理模块启动成功   ლ(´ڡ`ლ)ﾞ ");
    }
}
