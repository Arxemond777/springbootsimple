package com.net.amqp;

import java.util.concurrent.TimeUnit;

import com.net.spring.SpringApplicationSimple;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Producer implements CommandLineRunner
{

    private final RabbitTemplate rabbitTemplate;
    private final Consumer consumer;
    private final ConfigurableApplicationContext context;

    public Producer(Consumer consumer, RabbitTemplate rabbitTemplate, ConfigurableApplicationContext context) {

        this.consumer = consumer;
        this.rabbitTemplate = rabbitTemplate;
        this.context = context;

    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Sending message...");

        rabbitTemplate.convertAndSend(SpringApplicationSimple.queueName, "Hello from RabbitMQ!"); // Отправка при инициализации
        rabbitTemplate.convertAndSend(SpringApplicationSimple.queueName, "Hello from RabbitMQ!");

        consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        //context.close(); // Выход из контекста, после выполнения или timeout CountDownLatch

    }

}