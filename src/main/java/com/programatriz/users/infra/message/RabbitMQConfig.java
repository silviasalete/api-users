package com.programatriz.users.infra.message;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${msg.exchange.name}")
    private String exchangeName;

    @Value("${msg.routingKey.name}")
    private String routingKey;

    @Value("${msg.queue.name}")
    private String queueName;

    @Bean
    public Queue queue(){
        return new Queue(queueName);
    }

    @Bean
    public FanoutExchange exchange(){
        return new FanoutExchange(exchangeName);
    }

    @Bean
    public Binding binding(Queue queue, FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }
}
