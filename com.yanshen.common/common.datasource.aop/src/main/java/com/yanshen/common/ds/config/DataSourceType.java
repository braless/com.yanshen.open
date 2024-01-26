package com.yanshen.common.ds.config;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-26 16:01
 * @Description:
 * @Location: com.yanshen.common.datasource.config
 * @Project: com.yanshen.open
 */
/**
 * <p>
 * 枚举类，用于区分不同的数据源
 * </p>
 *
 */
public enum DataSourceType {
    /**
     * 主数据源
     */
    MASTER("master"),
    /**
     * 从数据源
     */
    SLAVE("slave");

    private final String value;

    DataSourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}