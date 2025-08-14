package com.programatriz.users.service;

import com.programatriz.users.controller.UserController;
import com.programatriz.users.infra.cache.UserLast;
import com.programatriz.users.repository.UserRepository;
import com.programatriz.users.model.User;
import com.programatriz.users.model.UserDto;
import com.programatriz.users.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;


    @Autowired
    private UserLast userLast;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public User create(UserDto dto ){
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        var user = new User(dto.name(), dto.email(), encryptedPassword,UserRole.valueOf(dto.role()));
        User userCreated = repository.save(user);
        userLast.cacheMemoryUserLast10Created();
        return userCreated;
    }

    @Override
    public User findByEmail(String email) {
        LOGGER.info("LOOKING FOR {} IN DATABASE",email);
        return repository.findByEmail(email);
    }

    @Override
    public Iterable<User> listAll() {
        return repository.findAll();
    }
}
