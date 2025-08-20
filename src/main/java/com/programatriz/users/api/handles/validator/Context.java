package com.programatriz.users.api.handles.validator;

import com.programatriz.users.model.User;
import com.programatriz.users.model.UserDto;

public class Context {

    private UserDto dto;
    public User user;

    public Context(UserDto dto, User user) {
        this.dto = dto;
        this.user = user;
    }

    public UserDto getDto() {
        return dto;
    }

    public void setDto(UserDto dto) {
        this.dto = dto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
