package com.programatriz.users.service;

import com.programatriz.users.model.User;
import com.programatriz.users.model.UserDto;

public interface UserService {
     User create(User user);
     Iterable<User> listAll();
     User findByEmail(String email);
     User findByEmailCacheable(String email);

}
