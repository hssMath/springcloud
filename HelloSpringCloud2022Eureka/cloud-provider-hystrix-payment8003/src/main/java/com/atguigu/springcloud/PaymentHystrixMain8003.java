package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient //将该服务注册进eureka服务：该服务被启动后会自动注册进eureka服务中
public class PaymentHystrixMain8003 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentHystrixMain8003.class,args);
    }
}
