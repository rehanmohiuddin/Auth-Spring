package com.sfuit.Auth.services;

import com.sfuit.Auth.entity.OtpEntity;
import com.sfuit.Auth.exceptions.EtBadRequestException;
import com.sfuit.Auth.exceptions.EtResourceNotFoundException;

import java.util.List;

public class OtpServiceImplementation implements OtpService{


    @Override
    public List<OtpEntity> fetchAllUsers(Integer userId) {
        return null;
    }

    @Override
    public OtpEntity fetchUserById(Integer userId, String phone) throws EtResourceNotFoundException {
        return null;
    }

    @Override
    public OtpEntity addUser(Integer userId, String phone, String otp, String token) throws EtBadRequestException {
        return null;
    }

    @Override
    public void deleteUserById(Integer userId, String phone) throws EtResourceNotFoundException {

    }
}
