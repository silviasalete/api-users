package com.programatriz.users.service;

import com.programatriz.users.model.User;
import com.programatriz.users.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;


    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public User create(User user){
        return repository.save(user);
    }

    @Override
//    @Cacheable("users") TODO it doesn't working
    public User findByEmail(String email) {
        LOGGER.info("Looking for {} in database...",email);
        try {
            return repository.findByEmail(email);
        } catch (IncorrectResultSizeDataAccessException e){
            LOGGER.error("ERROR: Exist duplicate data: {} Message error {}",email,e.getMessage());
            var listUser = repository.findAll();
            return listUser.iterator().next();
        }
    }

    @Override
    public Iterable<User> listAll() {
        return repository.findAll();
    }

}
