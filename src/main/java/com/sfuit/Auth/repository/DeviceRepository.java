package com.sfuit.Auth.repository;

import com.sfuit.Auth.entity.Devices;
import com.sfuit.Auth.exceptions.EtBadRequestException;
import com.sfuit.Auth.exceptions.EtResourceNotFoundException;

public interface DeviceRepository {

    Devices findByDeviceNum(Integer device_num) throws EtResourceNotFoundException;
    Devices findByDeviceId(String device_id) throws EtResourceNotFoundException;
    Integer create(String device_id, String sensors, String device_isverified) throws EtBadRequestException;
    Integer getCountByDeviceId(String device_id);

    Devices findByDeviceIdTemp(String device_id);
}
