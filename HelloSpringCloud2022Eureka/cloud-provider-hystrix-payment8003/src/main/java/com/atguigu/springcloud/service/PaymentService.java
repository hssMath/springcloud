package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id) {
        return "O(∩_∩)O，8003支付微服务的 paymentInfo_OK 方法执行成功了，执行的线程名称为：" + Thread.currentThread().getName();
    }

    /**
     * 开启服务端的使用 Hystrix 实现的服务降级功能。通过2个设置:设置兜底的封装好的友好提示对应的方法；设置服务降级的超时响应时间条件。
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties ={
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3500")
    })
    public String paymentInfo_TimeOut(Integer id) {
        int timeNumber = 0;
        try {
            timeNumber = 3000;
            new Thread().sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "O(∩_∩)O，8003支付微服务接口的 paymentInfo_TimeOut 方法执行了，线程名称：" + Thread.currentThread().getName() + "，查询id: " + id + "的信息，" + "耗费"+timeNumber/1000+"秒。";
    }

    /**
     * 向调用方返回一个符合预期的、可处理的备选响应（FallBack），而不是长时间的等待或者抛出调用方无法处理的异常：服务器忙，请稍后再试，不让客户端等待并立刻返回一个友好提示，fallback.
     * @param id
     * @return
     */
    public String paymentInfo_TimeOutHandler(Integer id) {
        return "(ㄒoㄒ)，8003支付微服务接口的 paymentInfo_TimeOut超时或异常了：\t"+ "\t当前线程池名字" + Thread.currentThread().getName();
    }



    //==========================服务熔断==========================================================
    //定义一个服务熔断的 CircuitBreaker 方法
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //请求总数阈值：默认为20，表示在10s内，如果 hystirx 命令的调用次数不足20次，即使所有的请求都超时或者其他原因失败，熔断器都不会打开。
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //时间窗口期（单位：ms）：在默认的时间间隔为10s期间，断路器打开统计，统计一些错误请求和数据。
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"), //失败率达到多少后跳闸：当请求总数在快照时间窗口期内超过了阈值，并且默认超过50%的错误百分比时，断路器才会打开。
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号: " + serialNumber;
    }
    //服务降低的兜底方法
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " + id;
    }
}