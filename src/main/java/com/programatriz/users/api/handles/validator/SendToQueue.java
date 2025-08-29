package com.programatriz.users.api.handles.validator;

import com.programatriz.users.api.UserController;
import com.programatriz.users.message.Producer;
import com.programatriz.users.model.User;
import com.programatriz.users.model.UserQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendToQueue extends Validator{

    private static final Logger LOGGER = LoggerFactory.getLogger(SendToQueue.class);

    private final Producer producer;

    public SendToQueue(Producer producer) {
        this.producer = producer;
    }

    @Override
    public User treat(Context context) {
        try {
            var userQueue = new UserQueue(
                    context.getUser().getName(),
                    context.getUser().getEmail(),
                    context.getUser().getRole().name());
            producer.sendUser(userQueue);
        } catch (Exception e){
            LOGGER.error("ERROR send to queue {}",e.getMessage());
        }
        return super.treat(context);
    }
}
