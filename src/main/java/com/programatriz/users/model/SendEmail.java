package com.programatriz.users.model;

public record SendEmail(String to, String typeEmail) {
    public SendEmail(String to, String typeEmail)  {
        this.to = to;
        this.typeEmail = typeEmail;
    }
}
