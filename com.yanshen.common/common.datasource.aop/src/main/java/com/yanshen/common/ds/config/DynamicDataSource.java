package com.yanshen.common.ds.config;

import lombok.NoArgsConstructor;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-26 16:04
 * @Description: 动态数据源类，继承了AbstractRoutingDataSource类，并实现了determineCurrentLookupKey()方法，用于获取当前的数据源
 * @Location: com.yanshen.common.datasource.config
 * @Project: com.yanshen.open
 */
@NoArgsConstructor
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> dataSourceHolder = new InheritableThreadLocal<>();

    /**
     * 设置数据源
     */
    public static void setDataSource(String dataSource) {
        dataSourceHolder.set(dataSource);
    }

    /**
     * 清除数据源
     */
    public static void clearDataSource() {
        dataSourceHolder.remove();
    }

    /**
     * 获取当前数据源
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceHolder.get();
    }
    /**
     * 设置默认数据源，和可切换的数据源Map
     * @param defaultTargetDataSource 默认数据源
     * @param targetDataSources 可切换的数据源Map
     */
    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }
}