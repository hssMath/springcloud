package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * zookeeper作为服务注册中心的整合
 */
@SpringBootApplication
@EnableDiscoveryClient  //该注解用于给服务注册中心（zookeeper、sonsul）注册服务的注解
public class PaymentMain8004 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8004.class, args);
    }
}
