package com.yanshen.common.security.annotation;

import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-19 17:08
 * @Description:
 * @Location: com.yanshen.common.security.annotation
 * @Project: com.yanshen.open
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableFeignClients
public @interface CustomFeignClients {
    String[] value() default {};

    String[] basePackages() default {"com.yanshen"};

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};
}
