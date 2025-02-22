package com.sfuit.Auth.entity;

public class User {

    private Integer userId;
    private String email;
    private String name;
    private String password;
    private String dob;
    private String phone;
    private String otp;
    private String is_verified;

    public User(Integer userId, String email, String name, String password, String dob, String phone, String otp, String is_verified) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.dob = dob;
        this.phone = phone;
        this.otp = otp;
        this.is_verified = is_verified;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(String is_verified) {
        this.is_verified = is_verified;
    }
}
