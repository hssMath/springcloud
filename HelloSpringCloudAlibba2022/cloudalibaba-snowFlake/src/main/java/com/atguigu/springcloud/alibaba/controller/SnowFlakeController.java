package com.atguigu.springcloud.alibaba.controller;

import com.atguigu.springcloud.alibaba.service.SnowFlakeIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SnowFlakeController {

    @Autowired
    private SnowFlakeIdService snowFlakeIdService;

    @GetMapping("/getSnowFlakeId")
    public String getSnowFlakeId() {
        return snowFlakeIdService.getIDBySnowFlake();
    }
    @GetMapping("/getMoreSnowFlakeId")
    public String getMoreSnowFlakeId() {
        snowFlakeIdService.getMoreSnowFlakeId();
        return "ok";
    }
}
