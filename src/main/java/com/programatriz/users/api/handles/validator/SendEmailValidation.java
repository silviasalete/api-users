package com.programatriz.users.api.handles.validator;

import com.programatriz.users.message.Producer;
import com.programatriz.users.model.SendEmail;
import com.programatriz.users.model.User;
import com.programatriz.users.service.HTTPService;

public class SendEmailValidation extends Validator{

    private HTTPService httpService;
    private Producer producer;

    public SendEmailValidation(HTTPService httpService, Producer producer) {
        this.httpService = httpService;
        this.producer = producer;
    }

    @Override
    public User treat(Context context){
        var sendEmail = new SendEmail(context.getUser().getEmail(),"valid");

        httpService.requestAPIEmail(sendEmail, producer);
        return context.getUser();
    }
}
