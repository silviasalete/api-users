package com.programatriz.users.message;

import com.programatriz.users.model.SendEmailQueue;
import com.programatriz.users.model.User;
import com.programatriz.users.model.UserDto;
import com.programatriz.users.model.UserQueue;
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

    @Value("${rabbitmq.exchange.email.direct}")
    private String emailExchangeDirect;

    @Value("${rabbitmq.exchange.user.fanout}")
    private String userExchangeFanout;

    @Value("${rabbitmq.routingkey.email}")
    private String emailRoutingkeyValidFail;


    public void sendUser(UserQueue userQueue){
        template.convertAndSend(userExchangeFanout, userQueue);
    }

    public void sendEmailValidFail(SendEmailQueue sendEmailQueue) {
        template.convertAndSend(emailExchangeDirect,emailRoutingkeyValidFail, sendEmailQueue);
    }
}
