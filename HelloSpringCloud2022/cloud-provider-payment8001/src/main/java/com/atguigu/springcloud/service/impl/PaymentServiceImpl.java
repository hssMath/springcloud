package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.dao.PaymentDao;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private PaymentDao paymentDaol;

    @Override
    public int add(Payment payment) {
        return paymentDaol.add(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDaol.getPaymentById(id);
    }
}
