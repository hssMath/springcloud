package com.atguigu.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 *  给容器中注册一个 RestTemplate 类型的 bean 对象：
 *  RestTemplate 提供了多种便捷访问远程 Http 服务的方法，是一种简单便捷的访问 restful 服务模板类，是 Spring 提供的用于访问 Rest 服务的客户端模板工具集
 */
@Configuration
public class ApplicationContextConfig {
    @Bean
//    @LoadBalanced   //支持负载均衡的功能：用于标记要配置为使用 LoadBalancerClient 的 RestTemplate bean 的注释
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
