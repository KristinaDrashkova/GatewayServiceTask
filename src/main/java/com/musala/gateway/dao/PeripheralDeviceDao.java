package com.musala.gateway.dao;

import com.musala.gateway.entities.PeripheralDevice;

import java.util.List;

public interface PeripheralDeviceDao {
    List<PeripheralDevice> findAll();

    PeripheralDevice findByUid(int uid);

    void save(PeripheralDevice peripheralDevice);

    void remove(int uid);
}
