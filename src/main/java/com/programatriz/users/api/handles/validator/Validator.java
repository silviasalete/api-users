package com.programatriz.users.api.handles.validator;

import com.programatriz.users.api.UserController;
import com.programatriz.users.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Validator {

    protected Validator next;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public User treat(Context context){
        if (next != null){
            LOGGER.info("FLOW {}",next.getClass().getSimpleName());
            return this.setNext(next).treat(context);
        }else {
            return null;
        }
    }

    public Validator setNext(Validator validator){
        this.next = validator;
        return validator;
    }
}
