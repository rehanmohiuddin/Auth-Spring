package com.sfuit.Auth.services;

import com.sfuit.Auth.entity.Devices;
import com.sfuit.Auth.entity.Token;
import com.sfuit.Auth.entity.User;
import com.sfuit.Auth.exceptions.EtAuthException;

public interface UserService {

    public User validateUser(String email, String password) throws EtAuthException;
    public User registerUser(String name, String email, String dob, String phone, String password, String otp, String token, String is_verified, String device_id) throws EtAuthException;
    public User verifyUser(String email, String otp) throws EtAuthException;

    public Token addToken(String email, String token, String device_id);
}
