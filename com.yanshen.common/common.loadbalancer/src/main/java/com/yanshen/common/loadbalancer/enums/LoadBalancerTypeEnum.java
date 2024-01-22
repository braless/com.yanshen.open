package com.yanshen.common.loadbalancer.enums;

public enum LoadBalancerTypeEnum {

    /**
     * 开发环境，获取自己的服务
     */
    DEV,

    /**
     * 网关，根据请求地址获取对应的服务
     */
    GATEWAY,

    /**
     * 轮循
     */
    ROUND_ROBIN,

    /**
     * 随机
     */
    RANDOM;

}
