package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixRolveBussinessConfuseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderHystirxResolveCodeSwellController {
    @Autowired
    private PaymentHystrixRolveBussinessConfuseService paymentHystrixRolveBussinessConfuseService;

    @GetMapping("/consumerresolve/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        String result = paymentHystrixRolveBussinessConfuseService.paymentInfo_OK(id);
        log.info("OrderHystirxController*****PaymentInfo_OK****result: " + result);
        return result;
    }

    /**
     * 开启服务调用者自己的使用 Hystrix 实现的服务降级功能,若未设置服务降级方法,使用全局的兜底方法
     */
    @GetMapping("/consumerresolve/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
//        int i = 10/0; //若程序出现运行异常时，直接调用服务降级方法,不会进行超时判断了.
        String result = paymentHystrixRolveBussinessConfuseService.paymentInfo_TimeOut(id);
        log.info("O(∩_∩)O，82微服务：PaymentInfo_TimeOut->result: " + result);
        return result;
    }

    /**
     * 兜底的降级方法
     * @param id
     * @return String
     */
    public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id) {
        return "o(╥﹏╥)o，82订单微服务调用8003支付微服务或者自己运行时出错,请稍后再试";
    }
}