springboot 整合 Rabbitmq

例子


讲解文章：

https://www.iocoder.cn/Spring-Boot/RabbitMQ/?self

源码地址：

https://github.com/yudaocode/SpringBoot-Labs/tree/master/lab-04-rabbitmq/lab-04-rabbitmq-demo

各种例子：

https://github.com/yudaocode/SpringBoot-Labs/tree/master/lab-04-rabbitmq

官方例子：

https://github.com/spring-projects/spring-amqp-samples

https://www.rabbitmq.com/tutorials


docker命令启动Rabbitmq，方便测试：

docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 -v rabbitmq-plugin:/plugins -e RABBITMQ_DEFAULT_USER=guest -e RABBITMQ_DEFAULT_PASS=123456 rabbitmq:3.13-management