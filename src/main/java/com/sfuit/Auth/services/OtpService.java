package com.sfuit.Auth.services;

import com.sfuit.Auth.entity.OtpEntity;
import com.sfuit.Auth.exceptions.EtBadRequestException;
import com.sfuit.Auth.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface OtpService {

    List<OtpEntity> fetchAllUsers(Integer userId);
    OtpEntity fetchUserById(Integer userId, String phone) throws EtResourceNotFoundException;
    OtpEntity addUser(Integer userId, String phone, String otp, String token) throws EtBadRequestException;
    void deleteUserById(Integer userId, String phone) throws EtResourceNotFoundException;
}
