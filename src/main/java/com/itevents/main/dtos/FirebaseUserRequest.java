package com.itevents.main.dtos;

public class FirebaseUserRequest {
    private String uid;
    private String email;
    private String username;
    private String loginProvider;

    // Getters y Setters
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getLoginProvider() {
        return loginProvider;
    }
    public void setLoginProvider(String loginProvider) {
        this.loginProvider = loginProvider;
    }
}
