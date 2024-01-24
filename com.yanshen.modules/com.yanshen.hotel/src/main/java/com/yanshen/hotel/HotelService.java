package com.yanshen.hotel;

import com.yanshen.common.security.annotation.CustomConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * hotel
 */
@SpringBootApplication
@CustomConfig
@EnableFeignClients
@EnableOpenApi
public class HotelService {

    public static void main(String[] args) {
        SpringApplication.run(HotelService.class, args);
    }
}
