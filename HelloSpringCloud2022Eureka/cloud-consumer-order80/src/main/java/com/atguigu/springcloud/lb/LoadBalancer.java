package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * 面向接口编程：实现该接口的方法，并注入到 spring 的容器中，就会让自定义的负载均衡算法生效。
 */
public interface LoadBalancer {
    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
