package com.programatriz.users.api.handles.validator;

import com.programatriz.users.model.User;
import com.programatriz.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUser  extends Validator{
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateUser.class);

    private final UserService service;

    public CreateUser(UserService service) {
        this.service = service;
    }

    @Override
    public User treat(Context context) {
        LOGGER.info("[FLOW-NEW-USER]  Creating user {}...",context.getUser().getRole().name());

        User created = service.create(context.getUser());
        if (!created.getId().isEmpty()){
            LOGGER.info("[FLOW-NEW-USER]  User {} created in databases!",context.getUser().getRole().name());
        }

        return super.treat(context);
    }
}
