package com.atguigu.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id) {
        return "线程池:" + Thread.currentThread().getName() + "paymentInfo_OK,id: " + id + "\t" + "O(∩_∩)O";
    }

    /**
     * 开启服务被调用者的使用 Hystrix 实现的服务降级功能。通过2个设置:设置兜底的封装好的友好提示对应的方法；设置服务降级的超时响应时间条件。
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties ={
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    } )
    public String paymentInfo_TimeOut(Integer id) {
        int timeNumber = 0;
        try {
            timeNumber = 3000;
            new Thread().sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "8003服务调用：paymentInfo_TimeOut方法，线程名称：" + Thread.currentThread().getName() + "，id: " + id + "，" + "O(∩_∩)O，耗费"+timeNumber/1000+"秒";
    }

    /**
     * 向调用方返回一个符合预期的、可处理的备选响应（FallBack），而不是长时间的等待或者抛出调用方无法处理的异常：服务器忙，请稍后再试，不让客户端等待并立刻返回一个友好提示，fallback.
     * @param id
     * @return
     */
    public String paymentInfo_TimeOutHandler(Integer id) {
        return "(ㄒoㄒ)调用支付接口超时或异常：\t"+ "\t当前线程池名字" + Thread.currentThread().getName();
    }
}