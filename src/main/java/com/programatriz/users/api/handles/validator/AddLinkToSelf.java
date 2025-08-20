package com.programatriz.users.api.handles.validator;

import com.programatriz.users.api.UserController;
import com.programatriz.users.model.User;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class AddLinkToSelf extends Validator{

    @Override
    public User treat(Context context) {
        context.getUser().add(WebMvcLinkBuilder.linkTo(methodOn(UserController.class).listAll()).withSelfRel());
        return context.getUser();
    }
}
