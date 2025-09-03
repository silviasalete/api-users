package com.programatriz.users.api.handles.validator;

import com.programatriz.users.model.User;
import com.programatriz.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.RedisConnectionFailureException;


public class UserAlreadyExist extends Validator {

    private UserService service;

    public UserAlreadyExist(UserService service) {
        this.service = service;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAlreadyExist.class);

    @Override
    public User treat(Context context) {
        LOGGER.info("[FLOW-NEW-USER] Checking if {} exist...",context.getDto().name());
        User user;
        try {
            user = showLogMessageUserExist(service.findByEmailCacheable(context.getDto().email()));
        } catch (RedisConnectionFailureException e ){
            LOGGER.error("[FLOW-NEW-USER] Fail on connection Redis. Message: {}",e.getMessage());
            user = showLogMessageUserExist(service.findByEmail(context.getDto().email()));
        } catch (Exception e ){
            LOGGER.error("[FLOW-NEW-USER] Happened some error while check if user exist. ");
            LOGGER.error("[FLOW-NEW-USER] Class exception {} ",e.getClass());
            return null;
        }

        return (user != null)? null : super.treat(context);

    }

    private User showLogMessageUserExist(User user){
        if (user != null) {
            LOGGER.warn("[FLOW-NEW-USER] User {} already exist ",user.getName());
        };
        return user;
    }
}
