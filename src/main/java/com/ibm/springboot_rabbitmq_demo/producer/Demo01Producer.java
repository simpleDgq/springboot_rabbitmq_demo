package com.ibm.springboot_rabbitmq_demo.producer;

import com.ibm.springboot_rabbitmq_demo.message.Demo01Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class Demo01Producer {
    // 使用 Spring-AMQP 封装提供的 RabbitTemplate ，实现发送消息

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id) {
        // 创建 Demo01Message 消息
        Demo01Message message = new Demo01Message();
        message.setId(id);
        // 同步发送消息
        // 指定 Exchange + RoutingKey ，从而路由到一个 Queue 中。
        rabbitTemplate.convertAndSend(Demo01Message.EXCHANGE, Demo01Message.ROUTING_KEY, message);
    }

    public void syncSendDefault(Integer id) {
        // 创建 Demo01Message 消息
        Demo01Message message = new Demo01Message();
        message.setId(id);
        // 同步发送消息
        /* 使用默认交换器。
            默认交换机的特点
            默认绑定：默认交换机会隐式地绑定到每个队列，绑定的路由键等于队列名称。
            路由规则：消息的路由键与队列的名称一致时，消息会被路由到该队列。
            不能显式指定交换机：此方法固定使用默认交换机。
            void convertAndSend(String routingKey, Object message) throws AmqpException;
         */

        rabbitTemplate.convertAndSend(Demo01Message.QUEUE, message);
    }

    /**
     * 也可以使用 AsyncRabbitTemplate 类，提供异步的 RabbitMQ 操作
     * 这里借助的是Spring的Async
     * @param id
     * @return
     */
    @Async
    public ListenableFuture<Void> asyncSend(Integer id) {
        try {
            // 发送消息
            this.syncSend(id);
            // 返回成功的 Future
            return AsyncResult.forValue(null);
        } catch (Throwable ex) {
            // 返回异常的 Future
            return AsyncResult.forExecutionException(ex);
        }
    }
}
