package com.programatriz.users.infra.cache;

import com.programatriz.users.controller.UserController;
import com.programatriz.users.model.User;
import com.programatriz.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UserLast {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserRepository userRepository;
    List<User> usersCached = new ArrayList<>();;

    @Bean
    public List<User> cacheMemoryUserLast10Created(){
        if (usersCached.isEmpty()){
            LOGGER.info("LOOADING CACHE...");
        }else {
            LOGGER.info("UPDATING CACHE...");

        }
        usersCached = userRepository.findTop10ByOrderByIdDesc(); 
        LOGGER.info("EXIST IN CACHE {} USERS",usersCached.size());
        return usersCached;
    }

    @Bean
    public List<User> getUserLast10Created(){
        return usersCached;
    }
}
