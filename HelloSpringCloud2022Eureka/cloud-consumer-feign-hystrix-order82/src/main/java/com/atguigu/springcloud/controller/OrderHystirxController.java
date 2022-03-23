package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 82模块调用8003模块，接口通用的和独享的 Fallback 方法各自分开，避免了代码膨胀，合理减少了代码量，O(∩_∩)O哈哈~
 */

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod") //1：N 除了个别重要核心业务有专属，其它普通的可以通过@DefaultProperties(defaultFallback = "")  统一跳转到统一处理结果页面
public class OrderHystirxController {
    @Resource
    private PaymentHystrixService paymentHystrixService;

    @HystrixCommand //加了 @DefaultProperties 属性注解，并且没有写具体方法名字，就使用统一全局的服务降级方法
    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfo_OK(id);
        log.info("OrderHystirxController*****PaymentInfo_OK****result: " + result);
        return result;
    }

    /**
     * 1:1  ->使用 Hystrix 给指定方法配置一个服务降级发生时降级方法。
     * 开启服务调用者自己的使用 Hystrix 实现的服务降级功能:给该请求设置一个单独的服务降级方法。
     */
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4500")
    })
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
//        int i = 10/0; //若程序出现运行异常时，直接调用服务降级方法,不会进行超时判断了.
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        log.info("O(∩_∩)O，82微服务：PaymentInfo_TimeOut->result: " + result);
        return result;
    }

    //兜底的 fallback 降级方法
    public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id) {
        return "o(╥﹏╥)o，82订单微服务调用8003支付微服务或者自己运行时出错,请稍后再试";
    }

    //全局fallback方法
    public String payment_Global_FallbackMethod(){
        return "Global异常处理信息，请稍后再试，(ㄒoㄒ)";
    }
}