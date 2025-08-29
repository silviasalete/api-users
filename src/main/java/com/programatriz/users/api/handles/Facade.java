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
        var userAlreadyExist = new UserAlreadyExist(service);
        userAlreadyExist
                .setNext(new RoleIsValid())
                .setNext(new SetUserRole())
                .setNext(new CriptPassword())
                .setNext(new CreateUser(service))
//                .setNext(new SendToQueue(producer)) TODO Só depois que ele estiver com a conta ativa
                .setNext(new SendEmailValidation(httpService, producer));

        return userAlreadyExist.treat(context);
    }
}
