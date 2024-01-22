package com.yanshen.common.loadbalancer.balancer;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import com.yanshen.common.core.util.IpUtils;
import com.yanshen.common.loadbalancer.enums.LoadBalancerTypeEnum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 自定义 SpringCloud 负载均衡算法
 * 负载均衡算法的默认实现是 {@link org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer}
 *
 * @author baohao-jia
 * @since 2023年12月12日 14:39
 */
@Slf4j
public class CustomSpringCloudLoadBalancer implements ReactorServiceInstanceLoadBalancer {
    private final String serviceId;
    private final AtomicInteger position;
    private final LoadBalancerTypeEnum type;
    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    public CustomSpringCloudLoadBalancer(String serviceId,
                                         LoadBalancerTypeEnum type,
                                         ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider) {
        this(serviceId, new Random().nextInt(1000), type, serviceInstanceListSupplierProvider);
    }

    public CustomSpringCloudLoadBalancer(String serviceId,
                                         int seedPosition,
                                         LoadBalancerTypeEnum type,
                                         ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider) {
        this.serviceId = serviceId;
        this.position = new AtomicInteger(seedPosition);
        this.type = type;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next().map(serviceInstances -> processInstanceResponse(request, supplier, serviceInstances));
    }

    private Response<ServiceInstance> processInstanceResponse(Request request,
                                                              ServiceInstanceListSupplier supplier,
                                                              List<ServiceInstance> serviceInstances) {
        Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(request, serviceInstances);
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }

    private Response<ServiceInstance> getInstanceResponse(Request request, List<ServiceInstance> instances) {
        if (instances.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("No servers available for service: " + serviceId);
            }
            return new EmptyResponse();
        }

        if (Objects.equals(type, LoadBalancerTypeEnum.ROUND_ROBIN)) {
            return this.getRoundRobinInstance(instances);
        } else if (Objects.equals(type, LoadBalancerTypeEnum.RANDOM)) {
            return this.getRandomInstance(instances);
        } else if (Objects.equals(type, LoadBalancerTypeEnum.DEV)) {
            return this.getDevelopmentInstance(instances);
        } else if (Objects.equals(type, LoadBalancerTypeEnum.GATEWAY)) {
            return this.getGatewayDevelopmentInstance(request, instances);
        }
        return this.getRoundRobinInstance(instances);
    }

    /**
     * 获取网关本机实例
     *
     * @param instances 实例
     * @return {@link Response }<{@link ServiceInstance }>
     * @author : lwq
     * @date : 2022-12-15 14:22:13
     */
    private Response<ServiceInstance> getGatewayDevelopmentInstance(Request request, List<ServiceInstance> instances) {

        //把request转为默认的DefaultRequest，从request中拿到请求的ip信息，再选择ip一样的微服务
        DefaultRequest<RequestDataContext> defaultRequest = Convert.convert(new TypeReference<DefaultRequest<RequestDataContext>>() {
        }, request);
        RequestDataContext context = defaultRequest.getContext();

        //先取得和本地ip一样的服务，如果没有则按默认来取
        for (ServiceInstance instance : instances) {
            String currentServiceId = instance.getServiceId();
            String host = instance.getHost();
            log.debug("注册服务：{}，ip：{}", currentServiceId, host);
            if (NetUtil.localIpv4s().contains(instance.getHost())) {
                return new DefaultResponse(instance);
            }
        }
        return getRoundRobinInstance(instances);
    }


    /**
     * 获取本机实例
     *
     * @param instances 实例
     * @return {@link Response }<{@link ServiceInstance }>
     * @author : lwq
     * @date : 2022-12-15 14:22:13
     */
    private Response<ServiceInstance> getDevelopmentInstance(List<ServiceInstance> instances) {
        //获取本机ip
        String hostIp = IpUtils.getHostIp();
        log.debug("本机Ip:{}", hostIp);

        //先取得和本地ip一样的服务，如果没有则按默认来取
        for (ServiceInstance instance : instances) {
            String currentServiceId = instance.getServiceId();
            String host = instance.getHost();
            log.debug("注册服务：{}，ip：{}", currentServiceId, host);
            if (StrUtil.isNotEmpty(host) && StrUtil.equals(hostIp, host)) {
                return new DefaultResponse(instance);
            }
        }
        return getRoundRobinInstance(instances);
    }

    /**
     * 使用随机算法
     * 参考{link {@link org.springframework.cloud.loadbalancer.core.RandomLoadBalancer}}
     *
     * @param instances 实例
     * @return {@link Response }<{@link ServiceInstance }>
     * @author : lwq
     * @date : 2022-12-15 13:32:11
     */
    private Response<ServiceInstance> getRandomInstance(List<ServiceInstance> instances) {
        int index = ThreadLocalRandom.current().nextInt(instances.size());
        ServiceInstance instance = instances.get(index);
        return new DefaultResponse(instance);
    }

    /**
     * 使用RoundRobin机制获取节点
     *
     * @param instances 实例
     * @return {@link Response }<{@link ServiceInstance }>
     * @author : lwq
     * @date : 2022-12-15 13:28:31
     */
    private Response<ServiceInstance> getRoundRobinInstance(List<ServiceInstance> instances) {
        // 每一次计数器都自动+1，实现轮询的效果
        int pos = this.position.incrementAndGet() & Integer.MAX_VALUE;
        ServiceInstance instance = instances.get(pos % instances.size());
        return new DefaultResponse(instance);
    }

}
