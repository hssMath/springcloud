package com.atguigu.springcloud.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)          //@EnableBinding：表示将信道 channel 和 exchange 绑定在一起；Sink：标识是该类是一个输入通道，通过该输入通道可以实现实时接收消息进入应用程序。
public class ReceiveMessageListener {
    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)     //监听队列，用于消费者的队列的消息接收。
    public void input(Message<String> message) {
        System.out.println("消费者1号，------->接收到的消息：" + message.getPayload() + "\t port: " + serverPort);
    }
}