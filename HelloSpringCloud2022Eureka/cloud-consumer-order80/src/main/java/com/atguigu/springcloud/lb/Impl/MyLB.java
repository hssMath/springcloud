package com.atguigu.springcloud.lb.Impl;

import com.atguigu.springcloud.lb.LoadBalancer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements LoadBalancer {
    //int类型的初始值。默认为0。
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 得到处理请求，集群服务下的服务下标
     * @return
     */
    public final int getAndIncrement() {
        int current;
        int next;
        do {//自旋锁
            current = this.atomicInteger.get();
            next = current >= Integer.MAX_VALUE ? 0 : current + 1;//Integer.MAX_VALUE:服务器的数量，保证自旋的时候数组不越界。一般情况下不会，加入项目很长时间没有重启，就可能出现数组越界问题。
        } while (!this.atomicInteger.compareAndSet(current, next));//CAS算法
        System.out.println("*****next: " + next);
        return next;
    }

    //负载均衡算法：rest接口第几次请求数 % 服务器集群的总数量 = 实际调用服务器位置的下标。每次重启服务后rest接口计数从1开始。
    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}

