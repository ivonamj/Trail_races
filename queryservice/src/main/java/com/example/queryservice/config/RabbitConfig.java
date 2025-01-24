package com.example.queryservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitConfig {

    @Bean
    public TopicExchange operationExchange() {
        log.info("Initializing TopicExchange: 'operation.topic'");
        return new TopicExchange("operation.topic");
    }

    @Bean
    public Queue raceQueue() {
        log.info("Initializing Queue: 'race.queue'");
        return new Queue("race.queue", true);
    }

    @Bean
    public Binding raceBinding(Queue raceQueue, TopicExchange operationExchange) {
        log.info("Binding Queue 'race.queue' to Exchange 'operation.topic' with routing key 'race.*'");
        return BindingBuilder.bind(raceQueue)
                .to(operationExchange)
                .with("race.*");
    }

    @Bean
    public Queue applicationQueue() {
        log.info("Initializing Queue: 'application.queue'");
        return new Queue("application.queue", true);
    }

    @Bean
    public Binding applicationBinding(Queue applicationQueue, TopicExchange operationExchange) {
        log.info("Binding Queue 'application.queue' to Exchange 'operation.topic' with routing key 'application.*'");
        return BindingBuilder.bind(applicationQueue)
                .to(operationExchange)
                .with("application.*");
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        log.debug("Setting up Jackson2JsonMessageConverter");
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        log.debug("Configuring SimpleRabbitListenerContainerFactory");
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        log.info("SimpleRabbitListenerContainerFactory configured successfully");
        return factory;
    }
}
