package com.ibm.springboot_rabbitmq_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringbootRabbitmqDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRabbitmqDemoApplication.class, args);
    }

}
