package com.programatriz.users.api.handles.validator;

import com.programatriz.users.api.UserController;
import com.programatriz.users.message.Producer;
import com.programatriz.users.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendToQueue extends Validator{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    private Producer producer;

    public SendToQueue(Producer producer) {
        this.producer = producer;
    }

    @Override
    public User treat(Context context) {
        try {
            producer.send(context.getUser());
        } catch (Exception e){
            LOGGER.error("ERROR send to queue{}",e.getMessage());
        }
        return super.treat(context);
    }
}
