package com.atguigu.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign 提供了日志打印功能，我们可以通过配置来调整日志级别，从而了解 Feign 中 Http 请求的细节。说白了就是对Feign接口的调用情况进行监控和输出。
 *      1、NONE：默认的，不显示任何日志；
 *      2、BASIC：仅记录请求方法、URL、响应状态码及执行时间；
 *      3、HEADERS：除了 BASIC 中定义的信息之外，还有请求和响应的头信息；
 *      4、FULL：除了 HEADERS 中定义的信息之外，还有请求和响应的正文及元数据。
 */
@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
