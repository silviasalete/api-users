package com.programatriz.users.service;

import com.programatriz.users.model.User;
import com.programatriz.users.model.UserDto;

public interface UserService {
     User create(UserDto dto );
     Iterable<User> listAll();
     User findByEmail(String email);
}
