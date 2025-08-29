package com.programatriz.users.api.handles.validator;

import com.programatriz.users.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class Validator {

    protected Validator next;

    public User treat(Context context){
        if (next == null) return null;
        return this.setNext(next).treat(context);
    }

    public Validator setNext(Validator validator){
        this.next = validator;
        return validator;
    }
}
