package com.sfuit.Auth.services;

import com.sfuit.Auth.entity.Devices;
import com.sfuit.Auth.exceptions.EtBadRequestException;

public interface DeviceService {

    public Devices addDevice(String device_id, String sensors, String device_isverified) throws EtBadRequestException;
    public Devices verifyDeviceID(String device_id) throws EtBadRequestException;
    public Devices deviceIdAvailable(String device_id) throws EtBadRequestException;
}
