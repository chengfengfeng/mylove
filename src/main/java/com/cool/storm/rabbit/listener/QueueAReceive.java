package com.cool.storm.rabbit.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
//@RabbitListener(queues = "QUEUE_A")
//public class QueueAReceive {
//
//    @RabbitHandler
//    public void process(String hello) {
//        System.out.println("QueueAReceive  : " + hello);
//    }
//}
