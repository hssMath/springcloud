package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonRusult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @PostMapping("value = /payment/create")
    public CommonRusult create(Payment payment) {
        int result = paymentService.add(payment);
        log.info("**插入结果：", result);
        if (result > 0) {
            return new CommonRusult(200, "插入数据成功");
        } else {
            return new CommonRusult(444, "插入数据失败");
        }
    }

    @GetMapping("value = /payment/getPaymentById/{id}")
    public CommonRusult getPaymentById(@Param("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("**查询结果：", payment);
        if (payment != null) {
            return new CommonRusult(200, "数据查询成功", payment.getId());
        } else {
            return new CommonRusult(444, "没有对应的记录，id:" + id, null);
        }
    }
}
