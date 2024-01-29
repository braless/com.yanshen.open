package com.yanshen.common.security.config;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-19 17:06
 * @Description:
 * @Location: com.yanshen.common.security
 * @Project: com.yanshen.open
 */

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.util.TimeZone;

/**
 * 系统配置
 *
 * @author Yanshen
 */
public class ApplicationConfig {
    /**
     * 时区配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
    }
}