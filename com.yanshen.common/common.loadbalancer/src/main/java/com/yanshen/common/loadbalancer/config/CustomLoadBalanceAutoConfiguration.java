package com.yanshen.common.loadbalancer.config;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;

/**
 * @author baohao-jia
 * @since 2023年12月12日 14:46
 */

@LoadBalancerClients(defaultConfiguration = CustomLoadBalanceClientConfiguration.class)
public class CustomLoadBalanceAutoConfiguration {
}
