package com.atguigu.springcloudalibaba.service.Impl;

import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloudalibaba.service.PaymentService;
import entities.CommonResult;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentService {
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(444, "服务降级返回,没有该流水信息", new Payment(id, "errorSerial......"));
    }
}
