package com.yanshen.common.loadbalancer.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.loadbalancer.NacosLoadBalancerClientConfiguration;

import com.yanshen.common.loadbalancer.balancer.NacosLocalFirstLoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

/**
 * nacos 负载均衡同IP 同区域有限
 *
 * @author baohao-jia
 * @since 2023年12月12日 11:01
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
// 这里引入 nacos 默认客户端配置，否则的话需要添加 配置 spring.cloud.loadbalancer.nacos.enabled = true
@Import(NacosLoadBalancerClientConfiguration.class)
@Slf4j
public class NacosLocalFirstLoadBalancerClientConfiguration {
    /**
     * 本地优先策略
     *
     * @param environment               环境变量
     * @param loadBalancerClientFactory 工厂
     * @param nacosDiscoveryProperties  属性
     * @return ReactorLoadBalancer
     */
    @Bean
    @ConditionalOnProperty(value = "spring.cloud.loadbalancer.local-first", havingValue = "true")
    public ReactorLoadBalancer<ServiceInstance> nacosLocalFirstLoadBalancer(Environment environment,
                                                                            LoadBalancerClientFactory loadBalancerClientFactory,
                                                                            NacosDiscoveryProperties nacosDiscoveryProperties) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        if (log.isDebugEnabled()) {
            log.debug("Use nacos local first load balancer for {} service", name);
        }
        return new NacosLocalFirstLoadBalancer(
                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class),
                name, nacosDiscoveryProperties);
    }
}
