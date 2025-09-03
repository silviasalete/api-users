package com.programatriz.users.api.handles.validator;

import com.programatriz.users.message.Producer;
import com.programatriz.users.model.SendEmail;
import com.programatriz.users.model.User;
import com.programatriz.users.service.HTTPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendEmailValidation extends Validator{
    private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailValidation.class);
    private HTTPService httpService;
    private Producer producer;

    public SendEmailValidation(HTTPService httpService, Producer producer) {
        this.httpService = httpService;
        this.producer = producer;
    }

    @Override
    public User treat(Context context){
        LOGGER.info("[FLOW-NEW-USER]  Sending validation link to {}!",context.getUser().getEmail());

        var sendEmail = new SendEmail(context.getUser().getEmail(),"valid");

        httpService.requestAPIEmail(sendEmail, producer);
        return context.getUser();
    }
}
