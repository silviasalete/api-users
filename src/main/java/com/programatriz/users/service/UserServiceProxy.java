package com.programatriz.users.service;

import com.programatriz.users.controller.UserController;
import com.programatriz.users.infra.cache.UserLast;
import com.programatriz.users.model.User;
import com.programatriz.users.model.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserServiceProxy implements UserService {

    private UserService userService;
    private UserLast usersCache;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceProxy.class);

    public UserServiceProxy(UserService userService, UserLast usersCache) {
        this.userService = userService;
        this.usersCache = usersCache;
    }

    @Override
    public User create(UserDto dto) {
        return null;
    }

    @Override
    public Iterable<User> listAll() {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        LOGGER.info("LOOKING FOR {} IN CACHE ...", email);
        User userReturned = null;
        for(User user: usersCache.getUserLast10Created()){
            if (user.getEmail().equals(email)){
                userReturned = user;
            }
        }
        if (userReturned != null){
            LOGGER.info("{} FOUND IN CACHE", email);
            return userReturned;
        }else{
            LOGGER.info("{} NOT FOUND IN CACHE", email);
            return  userService.findByEmail(email);
        }
    }
}
