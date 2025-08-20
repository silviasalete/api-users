package com.programatriz.users.api.handles.validator;

import com.programatriz.users.model.User;
import com.programatriz.users.service.UserService;

public class CreateUser  extends Validator{


    private UserService service;

    public CreateUser(UserService service) {
        this.service = service;
    }

    @Override
    public User treat(Context context) {
        service.create(context.getUser());
        return super.treat(context);
    }
}
