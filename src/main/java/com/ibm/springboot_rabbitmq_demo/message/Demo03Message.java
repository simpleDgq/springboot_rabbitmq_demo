package com.ibm.springboot_rabbitmq_demo.message;

public class Demo03Message {
    public static final String QUEUE_A = "QUEUE_DEMO_03_A";
    public static final String QUEUE_B = "QUEUE_DEMO_03_B";

    // Fanout Exchange 仅需要 Exchange 即可
    public static final String EXCHANGE = "EXCHANGE_DEMO_03";

    /**
     * 编号
     */
    private Integer id;

    public Demo03Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo03Message{" +
                "id=" + id +
                '}';
    }
}
