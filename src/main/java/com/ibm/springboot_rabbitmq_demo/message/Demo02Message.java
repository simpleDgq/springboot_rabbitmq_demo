package com.ibm.springboot_rabbitmq_demo.message;

public class Demo02Message {

    public static final String QUEUE = "QUEUE_DEMO_02";

    public static final String EXCHANGE = "EXCHANGE_DEMO_02";

    /**
     * 其中 "*" 用于匹配一个单词，"#" 用于匹配多个单词（可以是零个）。
     */
    public static final String ROUTING_KEY = "#.yu.nai";

    /**
     * 编号
     */
    private Integer id;

    public Demo02Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo02Message{" +
                "id=" + id +
                '}';
    }

}