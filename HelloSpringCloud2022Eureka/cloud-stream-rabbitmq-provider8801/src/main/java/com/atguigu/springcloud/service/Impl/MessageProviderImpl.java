package com.atguigu.springcloud.service.Impl;

import com.atguigu.springcloud.service.MessageProvider;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Component
@EnableBinding(Source.class)                //可以理解为是一个消息的发送管道的定义
public class MessageProviderImpl implements MessageProvider {
    @Resource
    private MessageChannel output;  // 消息的发送管道

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        this.output.send(MessageBuilder.withPayload(serial).build()); //创建并发送消息
        System.out.println("***serial: " + serial);

        return serial;
    }
}