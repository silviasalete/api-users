package com.programatriz.users.infra.message;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonEncoder;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.email.direct}")
    private String emailExchangeDirectName;

    @Value("${rabbitmq.exchange.user.fanout}")
    private String userExchangeFanoutName;

    @Value("${rabbitmq.routingkey.email}")
    private String emailRoutingkeyValidFail;

    @Value("${rabbitmq.queue.email.valid.fail}")
    private String queueEmailValidFailName;

    @Value("${rabbitmq.queue.user.new}")
    private String queueUserNewName;


    @Bean
    public Queue queueEmailValidFail(){
        return new Queue(queueEmailValidFailName);
    }

    @Bean
    public Queue queueNewUsers(){
        return new Queue(queueUserNewName);
    }

    @Bean
    public DirectExchange emailExchangeDirect() {
        return new DirectExchange(emailExchangeDirectName);
    }

    @Bean
    public FanoutExchange userExchangeFanout(){
        return new FanoutExchange(userExchangeFanoutName);
    }

    @Bean
    public Binding bindingEmailValidFail(Queue queueEmailValidFail, DirectExchange emailExchangeDirect){
        return BindingBuilder.bind(queueEmailValidFail).to(emailExchangeDirect).with(emailRoutingkeyValidFail);
    }

    @Bean
    public Binding bindingNewUsers(Queue queueNewUsers, FanoutExchange userExchangeFanout){
        return BindingBuilder.bind(queueNewUsers).to(userExchangeFanout);
    }

    @Bean
    public AmqpTemplate amqpTemplate(RabbitTemplate template){
         template.setMessageConverter(converter());
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
}
