package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients     //主启动类通过 @EnableFeignClients 开启 openFeign 组件，激活 openFeign 客户端的负载均衡功能。
public class OrderFeignMain81 {
    public static void main(String[] args) {
        SpringApplication.run(OrderFeignMain81.class, args);
    }
}