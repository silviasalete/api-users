package com.programatriz.users.service;

import com.programatriz.users.model.User;
import com.programatriz.users.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable("users") //    TODO To verify the reason it does working
    public User findByEmailCacheable(String email) {
        try {
            LOGGER.info("[FLOW-NEW-USER] Looking user by {} in cache...",email);
            return repository.findByEmail(email);
        } catch (IncorrectResultSizeDataAccessException e){
            LOGGER.error("[FLOW-NEW-USER] Exist duplicate data...");
            var listUser = repository.findAll();
            return listUser.iterator().next();
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            LOGGER.info("[FLOW-NEW-USER] Looking user by {} in database...",email);
            return repository.findByEmail(email);
        } catch (IncorrectResultSizeDataAccessException e){
            LOGGER.error("[FLOW-NEW-USER] Exist duplicate data in database...");
            var listUser = repository.findAll();
            return listUser.iterator().next();
        }
    }

    @Override
    public Iterable<User> listAll() {
        return repository.findAll();
    }

}
