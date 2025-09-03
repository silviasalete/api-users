package com.programatriz.users.api.handles.validator;

import com.programatriz.users.api.UserController;
import com.programatriz.users.model.User;
import com.programatriz.users.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoleIsValid extends Validator{

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleIsValid.class);

    @Override
    public User treat(Context context) {

        LOGGER.info("[FLOW-NEW-USER] Validating role {} ...",context.getDto().role());
        try {
            UserRole.valueOf(context.getDto().role());
            LOGGER.info("[FLOW-NEW-USER] Role {} is valid!",context.getDto().role());
            return super.treat(context);
        }catch (IllegalArgumentException e){
            LOGGER.error("[FLOW-NEW-USER] Role {} is invalid!",context.getDto().role());
            return null;
        }
    }
}
