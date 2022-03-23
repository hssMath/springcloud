package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableHystrix  //开启服务调用者 Hystrix，可以进行服务降级。
public class OrderHystrixMain82 {
    public static void main(String[] args) {
        SpringApplication.run(OrderHystrixMain82.class, args);
    }
}