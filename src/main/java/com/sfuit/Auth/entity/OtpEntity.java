package com.sfuit.Auth.entity;

public class OtpEntity {

    private Integer userId;
    private String phone;
    private String token;
    private String otp;

    public OtpEntity(Integer userId, String phone, String token, String otp) {
        this.userId = userId;
        this.phone = phone;
        this.token = token;
        this.otp = otp;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
