package com.programatriz.users.service;

import com.programatriz.users.repository.UserRepository;
import com.programatriz.users.model.User;
import com.programatriz.users.model.UserDto;
import com.programatriz.users.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@EnableCaching
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;


    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public User create(UserDto dto ){
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        var user = new User(dto.name(), dto.email(), encryptedPassword,UserRole.valueOf(dto.role()));
        return repository.save(user);
    }

    @Cacheable("users")
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
