package com.programatriz.users.api.handles.validator;

import com.programatriz.users.api.UserController;
import com.programatriz.users.model.User;
import com.programatriz.users.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetUserRole extends Validator{
    @Override
    public User treat(Context context) {
        context.getUser().setRole(UserRole.valueOf(context.getDto().role()));
        return super.treat(context);
    }
}
