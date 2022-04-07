package com.atguigu.springcloud.alibaba.service.Impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.atguigu.springcloud.alibaba.service.SnowFlakeIdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class SnowFlakeIdServiceImpl implements SnowFlakeIdService {
    private long workerId = 0;
    private long datacenterId = 0;
    private Snowflake snowflake = IdUtil.getSnowflake(workerId,workerId);

    @PostConstruct
    public void init(){
        workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        log.info("当前机器workerId:"+workerId);
    }

    @Override
    public String getIDBySnowFlake() {
        String id = snowflake.nextIdStr();
        log.info(id);
        return id;
    }

    @Override
    public void getMoreSnowFlakeId() {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 20; i++) {
            threadPool.submit(()->{
                System.out.println(snowflake.nextIdStr());
            });
        }
        threadPool.shutdown();
    }
}