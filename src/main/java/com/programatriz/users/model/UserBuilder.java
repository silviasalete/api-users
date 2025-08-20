package com.programatriz.users.model;

public class UserBuilder {
    
    private final User user;

    private UserBuilder() {
        user = new User();
    }

    public  static UserBuilder builder() {
        return new UserBuilder();
    }

    public UserBuilder name(String name){
        this.user.setName(name);
        return this;
    }

    public UserBuilder email(String email){
        this.user.setEmail(email);
        return this;
    }

    public UserBuilder password(String password){
        this.user.setPassword(password);
        return this;
    }

    public UserBuilder role(UserRole role){
        this.user.setRole(role);
        return this;
    }

    public User build(){
        return this.user;
    }
}
