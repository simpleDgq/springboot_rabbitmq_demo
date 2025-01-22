package com.ibm.springboot_rabbitmq_demo.message;

public class Demo04Message {
    public static final String QUEUE = "QUEUE_DEMO_04_A";

    public static final String EXCHANGE = "EXCHANGE_DEMO_04";

    public static final String HEADER_KEY = "color";
    public static final String HEADER_VALUE = "red";

    /**
     * 编号
     */
    private Integer id;

    public Demo04Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo04Message{" +
                "id=" + id +
                '}';
    }
}
