package com.programatriz.users.api.handles.validator;

import com.programatriz.users.api.UserController;
import com.programatriz.users.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CriptPassword extends Validator{

    @Override
    public User treat(Context context) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(context.getDto().password());
        context.getUser().setPassword(encryptedPassword);
        return super.treat(context);
    }
}
