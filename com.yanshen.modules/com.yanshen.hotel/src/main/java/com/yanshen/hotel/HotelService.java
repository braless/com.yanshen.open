package com.yanshen.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * hotel
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class HotelService {

    public static void main(String[] args) {
        SpringApplication.run(HotelService.class, args);
    }
}
