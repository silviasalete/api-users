package com.programatriz.users.message;

import com.programatriz.users.model.User;
import com.programatriz.users.model.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    private final RabbitTemplate template;

    public Producer(RabbitTemplate template) {
        this.template = template;
    }

    @Value("${msg.exchange.name}")
    private String exchangeName;

    @Value("${msg.routingKey.name}")
    private String routingKey;


    public void send(User user){
        template.convertAndSend(exchangeName, routingKey, user);
    }
}
