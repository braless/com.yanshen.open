package com.yanshen.common.ds.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-26 16:05
 * @Description:
 * @Location: com.yanshen.common.datasource.config
 * @Project: com.yanshen.open
 */
public class DataSourceConfig {


    /**
     * 创建数据源master
     * @return 数据源master
     */
    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 创建数据源slave
     * @return 数据源slave
     */
    @Bean(name = "slave")
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 数据源配置
     * @param master 数据源master
     * @param slave 数据源slave
     * @return 动态数据源切换对象。
     * @Description @Primary赋予该类型bean更高的优先级，使至少有一个bean有资格作为autowire的候选者。
     */
    @Bean
    @Primary
    public DataSource dataSource(@Qualifier("master") DataSource master,
                                 @Qualifier("slave") DataSource slave) {
        Map<Object, Object> dsMap = new HashMap<>(2);
        dsMap.put("master", master);
        dsMap.put("slave", slave);
        return new DynamicDataSource(master, dsMap);
    }
}
