package com.sfuit.Auth.services;

import com.sfuit.Auth.entity.Devices;
import com.sfuit.Auth.exceptions.EtBadRequestException;
import com.sfuit.Auth.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeviceServiceImplement implements DeviceService{

    @Autowired
    DeviceRepository deviceRepository;


    @Override
    public Devices addDevice(String device_id, String sensors, String device_isverified) throws EtBadRequestException {

        Integer count_devices = deviceRepository.getCountByDeviceId(device_id);

        if(count_devices>0)
            throw new EtBadRequestException("DeviceID already exists in SFUTI_DEVICES Table");

        int device_num = deviceRepository.create(device_id, sensors, device_isverified);
        return deviceRepository.findByDeviceNum(device_num);
    }

    @Override
    public Devices verifyDeviceID(String device_id) throws EtBadRequestException {
        if(device_id == null)
            throw new EtBadRequestException("DeviceID cant be null");
        return deviceRepository.findByDeviceId(device_id);
    }

    @Override
    public Devices deviceIdAvailable(String device_id) throws EtBadRequestException {
        if(device_id == null)
            throw new EtBadRequestException("DeviceID cant be null");
        return deviceRepository.findByDeviceIdTemp(device_id);
    }

}
