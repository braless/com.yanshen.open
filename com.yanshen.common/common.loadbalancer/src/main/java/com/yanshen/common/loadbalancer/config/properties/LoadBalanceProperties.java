package com.yanshen.common.loadbalancer.config.properties;

import com.yanshen.common.loadbalancer.enums.LoadBalancerTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 负载均衡配置项
 *
 * @author baohao-jia
 * @since 2023年12月12日 14:37
 */
@Data
@ConfigurationProperties(prefix = "spring.cloud.loadbalancer")
public class LoadBalanceProperties {

    private LoadBalancerTypeEnum type = LoadBalancerTypeEnum.DEV;
}
