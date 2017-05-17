package com.net.spring;

import com.net.amqp.Consumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;

/*@ComponentScan("com.net")
@SpringBootApplication
public class SpringApplicationSimple {

    public static void main(String[] args) {
        SpringApplication.run(SpringApplicationSimple.class, args);
    }
}*/

@ComponentScan("com.net")
@SpringBootApplication
public class SpringApplicationSimple extends SpringBootServletInitializer
{
    public final static String queueName = "spring-boot";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {

        return new TopicExchange("spring-boot-exchange");

    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with(queueName);

    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);

        return container;

    }

    @Bean
    MessageListenerAdapter listenerAdapter(Consumer receiver) { // Обрабатывает сообщения в очереди queueName. Зарегистрирован в SpringApplicationSimple.container

        return new MessageListenerAdapter(receiver, "receiveMessage");

    }

    /*TODO*/
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        return application.sources(SpringApplicationSimple.class);

    }

    public static void main(String[] args) {

        SpringApplication.run(SpringApplicationSimple.class, args);

    }
}