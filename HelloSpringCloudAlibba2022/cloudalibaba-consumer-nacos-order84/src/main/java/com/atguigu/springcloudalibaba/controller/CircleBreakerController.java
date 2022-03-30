package com.atguigu.springcloudalibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloudalibaba.service.PaymentService;
import com.sun.deploy.security.BlockedException;
import entities.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class CircleBreakerController {

    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/paymentSQL/{id}")
    @SentinelResource(value = "fallback",
            fallback = "handlerFallback",   //Java 运行异常兜底方法
            blockHandler = "blockHandler",  //sentinel 限流执行的兜底方法
            exceptionsToIgnore = {IllegalArgumentException.class})  //排除指定异常：先让程序走通，后期人工修复
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);
        if (id == 4) {
            throw new IllegalArgumentException("非法参数异常...");
        } else if (result.getData() == null) {
            throw new NullPointerException("空指针异常...");
        }
        return result;
    }

    /**
     * Java 运行异常兜底方法
     * @param id
     * @param exception
     * @return
     */
    public CommonResult<Payment> handlerFallback(@PathVariable("id") Long id, Throwable exception) {
        Payment payment = new Payment(id, null);
        return new CommonResult<>(444, "java 方法异常调用，兜底方法执行，异常内容:" + exception.getMessage(), payment);
    }

    /**
     * sentinel 限流执行的兜底方法
     * @param id
     * @param blockedException
     * @return
     */
    public CommonResult<Payment> blockHandler(@PathVariable("id") Long id, BlockedException blockedException) {
        Payment payment = new Payment(id, null);
        return new CommonResult<>(445, "sentinel 限流，兜底方法执行，异常内容:" + blockedException.getMessage(), payment);
    }

    //==============================OpenFeign=========================================
    @Resource
    private PaymentService paymentService;
    @GetMapping(value = "/consumer/paymentSQLByOpenfeign/{id}")
    public CommonResult<Payment> paymentSQLByOpenfeign(@PathVariable("id") Long id) {
        if (id == 4) {
            throw new RuntimeException("没有该id");
        }
        return paymentService.paymentSQL(id);
    }

}