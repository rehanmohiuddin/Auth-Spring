package com.sfuit.Auth.repository;

import com.sfuit.Auth.entity.User;
import com.sfuit.Auth.exceptions.EtAuthException;

public interface UserRepository {

    Integer create(String email, String name, String dob, String phone, String otp, String is_verified, String token) throws EtAuthException;

    User findByPhoneandOTP(String phone, String otp) throws EtAuthException;

    Integer getCountByEmail(String email);

    User findById(Integer userId);

    Integer getCountByPhone(String phone);
}
