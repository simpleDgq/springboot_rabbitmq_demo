package com.ibm.springboot_rabbitmq_demo.message;

// 要实现 Java Serializable 序列化接口。因为 RabbitTemplate 默认使用 Java 自带的序列化方式，进行序列化 POJO 类型的消息。
// RabbitConfig中使用了JSON序列化，这里就不用实现Serializable了。Serializable用于java序列化机制，而我们使用的是JSON序列化
// public class Demo01Message implements Serializable {
public class Demo01Message {
    public static final String QUEUE = "QUEUE_DEMO_01";

    public static final String EXCHANGE = "EXCHANGE_DEMO_01";

    public static final String ROUTING_KEY = "ROUTING_KEY_01";

    /**
     * 编号
     */
    private Integer id;

    public Demo01Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo01Message{" +
                "id=" + id +
                '}';
    }
}
