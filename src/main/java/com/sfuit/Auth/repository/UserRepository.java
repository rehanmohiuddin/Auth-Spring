package com.sfuit.Auth.repository;

import com.sfuit.Auth.entity.User;
import com.sfuit.Auth.exceptions.EtAuthException;

public interface UserRepository {

    Integer create(String email, String name, String password, String dob, String phone, String otp, String is_verified) throws EtAuthException;

    User findByEmailAndPassword(String email, String password) throws EtAuthException;

    Integer getCountByEmail(String email);

    User findById(Integer userId);
}
