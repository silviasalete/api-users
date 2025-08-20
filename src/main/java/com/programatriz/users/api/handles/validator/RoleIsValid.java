package com.programatriz.users.api.handles.validator;

import com.programatriz.users.api.UserController;
import com.programatriz.users.model.User;
import com.programatriz.users.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoleIsValid extends Validator{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Override
    public User treat(Context context) {
        try {
            UserRole.valueOf(context.getDto().role());
            LOGGER.info("Role is valid! ");
            return super.treat(context);
        }catch (IllegalArgumentException e){
            LOGGER.info("Role is invalid {}",context.getDto().role());
            return null;
        }
    }
}
