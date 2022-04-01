package com.atguigu.springcloud.alibaba.service;

import org.springframework.web.bind.annotation.RequestParam;

public interface StorageService {
    /**
     * 扣减库存：2个参数从 Feign 调用中获取
     */
    void decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}
