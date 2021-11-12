package com.sfuit.Auth.entity;

public class Token {

    Integer token_id;
    String email;
    String updated_token;
    String device_id;

    public Token(Integer token_id, String email, String updated_token, String device_id) {
        this.token_id = token_id;
        this.email = email;
        this.updated_token = updated_token;
        this.device_id = device_id;
    }

    public Integer getToken_id() {
        return token_id;
    }

    public void setToken_id(Integer token_id) {
        this.token_id = token_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUpdated_token() {
        return updated_token;
    }

    public void setUpdated_token(String updated_token) {
        this.updated_token = updated_token;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
}
