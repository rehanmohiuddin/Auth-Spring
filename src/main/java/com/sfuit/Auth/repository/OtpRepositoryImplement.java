package com.sfuit.Auth.repository;

import com.sfuit.Auth.entity.OtpEntity;
import com.sfuit.Auth.exceptions.EtBadRequestException;
import com.sfuit.Auth.exceptions.EtResourceNotFoundException;

import java.util.List;

public class OtpRepositoryImplement implements OtpRepository{
    @Override
    public List<OtpEntity> findAllRows(Integer userId) throws EtResourceNotFoundException {
        return null;
    }

    @Override
    public OtpEntity findById(Integer userId, String phone) throws EtResourceNotFoundException {
        return null;
    }

    @Override
    public Integer create(Integer userId, String phone, String token, String otp) throws EtBadRequestException {
        return null;
    }

    @Override
    public void removeById(Integer userId) {

    }
}
