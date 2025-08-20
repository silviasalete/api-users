package com.programatriz.users.api.handles.validator;

import com.programatriz.users.api.UserController;
import com.programatriz.users.model.User;
import com.programatriz.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAlreadyExist extends Validator {

    private UserService service;

    public UserAlreadyExist(UserService service) {
        this.service = service;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Override
    public User treat(Context context) {
        LOGGER.info("FLOW started ... {}",this.getClass().getSimpleName());
        User user = service.findByEmail(context.getDto().email());
        if (user == null){
            LOGGER.info("USER NOT EXIST");
            return super.treat(context);
        }else{
            LOGGER.info("USER EXIST");
            return null;
        }
    }
}
