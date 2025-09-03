package com.programatriz.users.message;

import com.programatriz.users.model.SendEmailQueue;
import com.programatriz.users.model.UserQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class Producer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

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
        LOGGER.info("[FLOW-NEW-USER] Sending message {}",sendEmailQueue.getTypeEmail());
        LOGGER.info("[FLOW-NEW-USER] Exchange: {} | Routing key: {}",emailExchangeDirect, emailRoutingkeyValidFail);

        template.convertAndSend(emailExchangeDirect,emailRoutingkeyValidFail, sendEmailQueue);
    }
}
