package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class OrderZKController {

    public static final String INOKE_URL = "http://cloud-provider-payment";    //zookeeper 服务注册中心注册服务的调用地址

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/payment/zk")
    public String paymentInfo() {
        String result = restTemplate.getForObject(INOKE_URL + "/payment/zk", String.class);
        log.info("消费者调用支付服务(zookeeper)--->result:{}" + result);
        return result;
    }
}

