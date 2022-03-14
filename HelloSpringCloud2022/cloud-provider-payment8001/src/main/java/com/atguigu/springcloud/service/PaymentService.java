package com.atguigu.springcloud.service;

import entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {
    public int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);
}
