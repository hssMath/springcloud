package com.atguigu.springcloud.service.Impl;

import com.atguigu.springcloud.service.PaymentHystrixRolveBussinessConfuseService;
import org.springframework.stereotype.Component;

/**
 * 通过一个 feign接口 的实现类,就可以统一的对接口里面的方法进行处理.从而解决了干活的支付微服务 Hystrix 的代码膨胀 和 业务与逻辑代码混乱的问题.
 */
@Component
public class PaymentHystrixRolveBussinessConfuseServiceImpl implements PaymentHystrixRolveBussinessConfuseService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "o(╥﹏╥)o，82订单微服务调用8003支付微服务 paymentInfo_OK 接口或者自己运行时出错,请稍后再试";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "o(╥﹏╥)o，82订单微服务调用8003支付微服务  paymentInfo_TimeOut 接口 或者自己运行时出错,请稍后再试";
    }
}
