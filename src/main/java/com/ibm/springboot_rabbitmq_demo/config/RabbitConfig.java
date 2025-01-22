package com.ibm.springboot_rabbitmq_demo.config;

import com.ibm.springboot_rabbitmq_demo.message.Demo01Message;
import com.ibm.springboot_rabbitmq_demo.message.Demo02Message;
import com.ibm.springboot_rabbitmq_demo.message.Demo03Message;
import com.ibm.springboot_rabbitmq_demo.message.Demo04Message;
import org.springframework.amqp.core.*;
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

    /**
     * Topic Exchange 示例的配置类
     */
    public static class TopicExchangeDemoConfiguration {

        // 创建 Queue
        @Bean
        public Queue demo02Queue() {
            return new Queue(Demo02Message.QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Topic Exchange
        @Bean
        public TopicExchange demo02Exchange() {
            return new TopicExchange(Demo02Message.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding
        // Exchange：Demo02Message.EXCHANGE
        // Routing key：Demo02Message.ROUTING_KEY
        // Queue：Demo02Message.QUEUE
        @Bean
        public Binding demo02Binding() {
            return BindingBuilder.bind(demo02Queue()).to(demo02Exchange()).with(Demo02Message.ROUTING_KEY);
        }

    }


    /**
     * Fanout Exchange 示例的配置类
     */
    public static class FanoutExchangeDemoConfiguration {

        // 创建 Queue A
        @Bean
        public Queue demo03QueueA() {
            return new Queue(Demo03Message.QUEUE_A, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Queue B
        @Bean
        public Queue demo03QueueB() {
            return new Queue(Demo03Message.QUEUE_B, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Fanout Exchange
        @Bean
        public FanoutExchange demo03Exchange() {
            return new FanoutExchange(Demo03Message.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding A
        // Exchange：Demo03Message.EXCHANGE
        // Queue：Demo03Message.QUEUE_A
        @Bean
        public Binding demo03BindingA() {
            return BindingBuilder.bind(demo03QueueA()).to(demo03Exchange());
        }

        // 创建 Binding B
        // Exchange：Demo03Message.EXCHANGE
        // Queue：Demo03Message.QUEUE_B
        @Bean
        public Binding demo03BindingB() {
            return BindingBuilder.bind(demo03QueueB()).to(demo03Exchange());
        }

    }


    /**
     * Headers Exchange 示例的配置类
     */
    public static class HeadersExchangeDemoConfiguration {

        // 创建 Queue
        @Bean
        public Queue demo04Queue() {
            return new Queue(Demo04Message.QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Headers Exchange
        @Bean
        public HeadersExchange demo04Exchange() {
            return new HeadersExchange(Demo04Message.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding
        // Exchange：Demo04Message.EXCHANGE
        // Queue：Demo04Message.QUEUE
        // Headers: Demo04Message.HEADER_KEY + Demo04Message.HEADER_VALUE
        @Bean
        public Binding demo4Binding() {
            return BindingBuilder.bind(demo04Queue()).to(demo04Exchange())
                    .where(Demo04Message.HEADER_KEY).matches(Demo04Message.HEADER_VALUE); // 配置 Headers 匹配
        }

        /**
         * Headers Exchange 不依赖于 routing key 与 binding key 的匹配规则来路由消息，而是根据发送的消息内容中的 headers 属性进行匹配。
         *
         * 在绑定 Queue 与 Exchange 时指定一组 headers 键值对。
         * 当消息发送到 Exchange 时，RabbitMQ 会取到该消息的 headers（也是一个键值对的形式），对比其中的键值对是否完全匹配
         * Queue 与 Exchange 绑定时指定的键值对；
         * 如果完全匹配则消息会路由到该 Queue ，否则不会路由到该 Queue 。
         *
         * 不过艿艿在查询资料的时候，有资料说 Headers Exchange 性能很差，实际场景也使用比较少。所以本小节的内容，胖友可以选择性看。
         */

    }
}
