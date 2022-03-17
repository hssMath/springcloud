package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonRusult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 支付模块，用于订单模块进行跨模块调用
 */

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    /**
     * 插入数据，将前台的参数封装为对象的属性值
     * @param payment
     * @return
     */
    @PostMapping(value = "/payment/create")
    public CommonRusult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("**插入结果：", result);
        if (result > 0) {
            return new CommonRusult(200, "插入数据成功,serverPort"+serverPort,result);
        } else {
            return new CommonRusult(444, "插入数据失败");
        }
    }

    @GetMapping(value = "/payment/getPaymentById/{id}")
    public CommonRusult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("**查询结果：{}", payment);
        if (payment != null) {
            return new CommonRusult(200, "数据查询成功,serverPort"+serverPort, payment);
        } else {
            return new CommonRusult(444, "没有对应的记录，id:" + id, null);
        }
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        log.info("PaymentMain8002 注册到 eureka 服务器，通过自定义的<轮询>负载均衡算法，访问该服务下的请求，该服务器的端口为{}。",serverPort);
        return serverPort;
    }
}
