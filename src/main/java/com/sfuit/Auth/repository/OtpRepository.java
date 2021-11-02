package com.sfuit.Auth.repository;

import com.sfuit.Auth.entity.OtpEntity;
import com.sfuit.Auth.exceptions.EtBadRequestException;
import com.sfuit.Auth.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface OtpRepository {

    List<OtpEntity> findAllRows(Integer userId) throws EtResourceNotFoundException;
    OtpEntity findById(Integer userId, String phone) throws EtResourceNotFoundException;
    Integer create(Integer userId, String phone, String token, String otp) throws EtBadRequestException;
    void removeById(Integer userId);

}
