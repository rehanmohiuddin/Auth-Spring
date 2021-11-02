package com.sfuit.Auth.services;

import com.sfuit.Auth.entity.User;
import com.sfuit.Auth.exceptions.EtAuthException;

public interface UserService {

    public User validateUser(String phone, String otp) throws EtAuthException;
    public User registerUser(String email, String name, String dob, String phone, String otp, String is_verified, String token) throws EtAuthException;

}
