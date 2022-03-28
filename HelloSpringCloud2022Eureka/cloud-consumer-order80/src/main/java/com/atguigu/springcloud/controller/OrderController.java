package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonRusult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * 微服务实例之间的调用，模块之间的调用。
 *      正确的做法是使用 httpClient，这里使用的是 RestTempalte(对 httpClient 的封装)，也可以使用注入 service 的方式。
 * 1.订单模块调用支付模块，实际是将请求在客户端模块使用 httpClient 将请求将请求进行了一个构造，然后通过构造的请求进行模块之间的通信。
 */

@RestController
@Slf4j
public class OrderController {
    @Resource
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;//可以获取注册中心上的服务列表

    @Autowired
    private LoadBalancer loadBalancer;

    /**
     * 写入门程序时，这块的地址和端口是写死的，但是当支付模块处于集群模式时，对外暴漏的不再是 url和 port，此时设置为集群模式下模块在 eureka 下注册的微服务名称。
     * 想让 eureka 决定使用哪个微服务提供服务与相应，就需要对容器中的 restTemplate 对象添加一个 @LoadBalanced ,实现 restTemplate 的负载均衡能力。
     * 1.此时，使用的是 “轮询”的默认机制
     * 2.Ribbon 和 Eureka 整合后 Consumer 可以直接调用 payment 服务而不用再关心地址和端口号（因为 eureka 可以通过服务名映射了 payment 服务的ip 和 端口），且该服务还有负载功能了。O(∩_∩)O
     */
//    static final String PAYMENT_URL = "http://localhost:8001";        //单机环境下，微服务集群的使用
    static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";   //集群环境下，微服务集群的使用

    @GetMapping("/consumer/payment/create")//客户端：发送查询请求
    public CommonRusult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonRusult.class);//传输对象使用的请求 postForObject
    }

    /**
     * restTemplate.postForObject:返回对象为响应体中数据转化成的对象，基本上可以理解为Json
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/getPaymentById/{id}")
    public CommonRusult<Payment> getPayMent(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/getPaymentById/" + id, CommonRusult.class); //查询对象请求使用 getForObject
    }

    /**
     * restTemplate.getForEntity:返回对象为ResponseEntity对象，包含了响应中的一些重要信息，比如响应头、响应状态码、响应体等
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonRusult<Payment> getForEntity(@PathVariable("id") Long id) {
        ResponseEntity<CommonRusult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/getPaymentById/" + id, CommonRusult.class);//查询对象请求使用 getForObject
        log.info("restTemplate 的 getForEntity方法，查询的相应完整对象为：{}",entity);
        if (entity.getStatusCode().is2xxSuccessful()){
            return  entity.getBody();
        }else {
            return  new CommonRusult(444,"操作失败");
        }
    }


    @GetMapping("/consumer/payment/lb")
    public String getPaymentLoadBalancer(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(instances == null || instances.size()<=0) {
            return null;
        }
        ServiceInstance serviceInstance = loadBalancer.instances(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }


    // ====================> zipkin+sleuth======================
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin() {
        String result = restTemplate.getForObject(PAYMENT_URL + "/payment/zipkin", String.class);
        return result;
    }
}

