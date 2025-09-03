package com.programatriz.users.api.handles;

import com.programatriz.users.api.handles.validator.*;
import com.programatriz.users.message.Producer;
import com.programatriz.users.model.User;
import com.programatriz.users.model.UserBuilder;
import com.programatriz.users.model.UserDto;
import com.programatriz.users.service.HTTPService;
import com.programatriz.users.service.UserService;

public class Facade {

    private final UserDto dto;
    private final UserService service;
    private final Producer producer;
    private HTTPService httpService;

    public Facade(UserDto dto, UserService service, Producer producer, HTTPService httpService) {
        this.dto = dto;
        this.service = service;
        this.producer = producer;
        this.httpService = httpService;
    }

    public User crateUser(){

        var context = new Context(dto, UserBuilder.builder().email(dto.email()).name(dto.name()).build());
        var roleIsValid = new RoleIsValid();
        roleIsValid
                .setNext(new SetUserRole())
                .setNext(new CriptPassword())
                .setNext(new UserAlreadyExist(service))
                .setNext(new CreateUser(service))
//                .setNext(new SendToQueue(producer)) TODO Only after count is active
                .setNext(new SendEmailValidation(httpService, producer));

        return roleIsValid.treat(context);
    }
}
