package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    @Bean
    @LoadBalanced   //需要对容器中的 restTemplate 对象添加一个 @LoadBalanced ,实现 restTemplate 的负载均衡能力。
    public RestTemplate GetRestTemplate() {
        return new RestTemplate();
    }

}
