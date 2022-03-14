package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonRusult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 服务之间的调用，模块之间的调用。正确的做法是使用 httpClient，这里使用的是 RestTempalte(对 httpClient 的封装)，也不大可能使用注入 service 的方式。
 * 1.订单模块调用支付模块，实际是将请求在客户端模块使用 httpClient 将请求将请求进行了一个构造，然后通过构造的请求进行模块之间的通信。
 */

@RestController
@Slf4j
public class OrderController {

    @Resource
    private RestTemplate restTemplate;

    static final String PAYMENT_URL = "http://localhost:8001";

    @GetMapping("/consumer/payment/create")//客户端：发送查询请求
    public CommonRusult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonRusult.class);//传输对象请求使用 postForObject
    }

    @GetMapping("/consumer/payment/getPaymentById/{id}")
    public CommonRusult<Payment> getPayMent(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/getPaymentById/" + id, CommonRusult.class); //查询对象请求使用 getForObject
    }
}

