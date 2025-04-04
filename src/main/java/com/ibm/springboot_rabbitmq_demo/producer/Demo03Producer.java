package com.ibm.springboot_rabbitmq_demo.producer;

import com.ibm.springboot_rabbitmq_demo.message.Demo03Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Demo03Producer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id) {
        // 创建 Demo03Message 消息
        Demo03Message message = new Demo03Message();
        message.setId(id);
        // 同步发送消息
        // 传入 routingKey = null ，因为fanout不需要routingKey
        rabbitTemplate.convertAndSend(Demo03Message.EXCHANGE, null, message);
    }
}
