package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonRusult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")    //用在客户端的接口,给业务类接口加上 @FeignClient 注解，告诉客户端调用微服务的名称。
public interface PaymentFeignService {
    @GetMapping(value = "/payment/getPaymentById/{id}")
    CommonRusult getPaymentById(@PathVariable("id") Long id);

    @GetMapping(value = "/payment/feign/timeout")
    String paymentFeignTimeOut();
}