package com.sfuit.Auth.entity;

public class Devices {

    Integer device_num;
    String device_id;
    String sensors;
    String device_isverified;

    public Devices(Integer device_num, String device_id, String sensors, String device_isverified) {
        this.device_num = device_num;
        this.device_id = device_id;
        this.sensors = sensors;
        this.device_isverified = device_isverified;
    }

    public Integer getDevice_num() {
        return device_num;
    }

    public void setDevice_num(Integer device_num) {
        this.device_num = device_num;
    }

    public String getDevice_isverified() {
        return device_isverified;
    }

    public void setDevice_isverified(String device_isverified) {
        this.device_isverified = device_isverified;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }


    public String getSensors() {
        return sensors;
    }

    public void setSensors(String sensors) {
        this.sensors = sensors;
    }
}
