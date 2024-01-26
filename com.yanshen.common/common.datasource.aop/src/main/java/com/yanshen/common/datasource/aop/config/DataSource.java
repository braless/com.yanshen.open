package com.yanshen.common.datasource.aop.config;


import java.lang.annotation.*;

/**
 * <p>
 * 注解类，用于标识需要使用哪个数据源
 * </p>
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    DataSourceType value() default DataSourceType.MASTER;
}