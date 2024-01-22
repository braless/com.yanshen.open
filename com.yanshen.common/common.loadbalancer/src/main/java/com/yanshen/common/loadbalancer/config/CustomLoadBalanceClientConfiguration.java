package com.yanshen.common.loadbalancer.config;

import com.yanshen.common.loadbalancer.balancer.CustomSpringCloudLoadBalancer;
import com.yanshen.common.loadbalancer.config.properties.LoadBalanceProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author baohao-jia
 * @since 2023年12月12日 14:45
 */
@SuppressWarnings("all")
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LoadBalanceProperties.class)
public class CustomLoadBalanceClientConfiguration {

    @Bean
    @ConditionalOnBean(LoadBalancerClientFactory.class)
    public ReactorLoadBalancer<ServiceInstance> customLoadBalancer(LoadBalanceProperties loadBalanceProperties,
                                                                   Environment environment,
                                                                   LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new CustomSpringCloudLoadBalancer(name, loadBalanceProperties.getType(),
                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class));
    }

}
