package com.programatriz.users.model;

import java.io.Serializable;

public class SendEmailQueue implements Serializable {
    private String to;
    private String typeEmail;

    public SendEmailQueue(String to, String typeEmail) {
        this.to = to;
        this.typeEmail = typeEmail;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTypeEmail() {
        return typeEmail;
    }

    public void setTypeEmail(String typeEmail) {
        this.typeEmail = typeEmail;
    }
}
