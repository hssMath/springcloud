package com.atguigu.springcloud.alibaba.service;

public interface SnowFlakeIdService {
    String getIDBySnowFlake();

    void getMoreSnowFlakeId();
}
