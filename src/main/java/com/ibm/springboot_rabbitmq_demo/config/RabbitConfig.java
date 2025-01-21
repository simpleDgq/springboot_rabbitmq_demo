package com.ibm.springboot_rabbitmq_demo.config;

import com.ibm.springboot_rabbitmq_demo.message.Demo01Message;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    /**
     * 下面的两种MessageConverter都可以用来解决下面的这个异常
     *
     * Caused by: java.lang.SecurityException: Attempt to deserialize unauthorized
     * class com.ibm.springboot_rabbitmq_demo.message.Demo01Message; add allowed
     * class name patterns to the message converter or, if you trust the message
     * originator, set environment variable 'SPRING_AMQP_DESERIALIZATION_TRUST_ALL'
     * or system property 'spring.amqp.deserialization.trust.all' to true
     * @return
     */

//    @Bean
//    public MessageConverter jsonToMapMessageConverter() {
//        DefaultClassMapper defaultClassMapper = new DefaultClassMapper();
//        defaultClassMapper.setTrustedPackages("cn.baidu.kkscsp.common.mq"); // trusted packages
//        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//        jackson2JsonMessageConverter.setClassMapper(defaultClassMapper);
//        return jackson2JsonMessageConverter;
//    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.addTrustedPackages("com.ibm.springboot_rabbitmq_demo.message");
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

    /**
     * Direct Exchange 示例的配置类
     */
    public static class DirectExchangeDemoConfiguration {

        // 创建 Queue
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，有消息者订阅本队列，然后所有消费者都解除订阅此队列，会自动删除。
        // arguments：队列携带的参数，比如设置队列的死信队列，消息的过期时间等等。
        @Bean
        public Queue demo01Queue() {
            return new Queue(Demo01Message.QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Direct Exchange
        // durable:是否持久化,默认是false,持久化交换机。
        // autoDelete:是否自动删除，交换机先有队列或者其他交换机绑定的时候，然后当该交换机没有队列或其他交换机绑定的时候，会自动删除。
        // arguments：交换机设置的参数，比如设置交换机的备用交换机（Alternate Exchange），当消息不能被路由到该交换机绑定的队列上时，会自动路由到备用交换机
        @Bean
        public DirectExchange demo01Exchange() {
            return new DirectExchange(Demo01Message.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding
        // Exchange：Demo01Message.EXCHANGE
        // Routing key：Demo01Message.ROUTING_KEY
        // Queue：Demo01Message.QUEUE
        @Bean
        public Binding demo01Binding() {
            return BindingBuilder.bind(demo01Queue()).to(demo01Exchange()).with(Demo01Message.ROUTING_KEY);
        }

    }
}
