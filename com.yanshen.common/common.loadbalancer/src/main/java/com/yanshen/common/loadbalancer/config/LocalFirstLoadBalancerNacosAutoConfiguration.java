package com.yanshen.common.loadbalancer.config;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义负载均衡自动配置
 *
 * @author baohao-jia
 * @since 2023年12月12日 11:03
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties
@ConditionalOnNacosDiscoveryEnabled
@LoadBalancerClients(defaultConfiguration = NacosLocalFirstLoadBalancerClientConfiguration.class)
public class LocalFirstLoadBalancerNacosAutoConfiguration {
}
