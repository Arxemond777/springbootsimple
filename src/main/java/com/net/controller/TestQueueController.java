package com.net.controller;

import com.net.amqp.Consumer;
import com.net.spring.SpringApplicationSimple;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class TestQueueController
{

    private final RabbitTemplate rabbitTemplate;
    private final Consumer consumer;

    public TestQueueController(Consumer consumer, RabbitTemplate rabbitTemplate) {

        this.consumer = consumer;
        this.rabbitTemplate = rabbitTemplate;

    }

    @RequestMapping(value = "/test_queue", method = RequestMethod.GET)
    public String testQueue() {

        String message = "Message from " + this.getClass().getName() + " Date: " + new Date();

        rabbitTemplate.convertAndSend(SpringApplicationSimple.queueName, message);

        return "Message: \"" + message + "\" send in queue with name: \"" + SpringApplicationSimple.queueName + " in " + new Date();

    }
}
