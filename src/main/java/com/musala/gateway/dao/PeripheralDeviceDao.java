package com.musala.gateway.dao;

import com.musala.gateway.entities.PeripheralDevice;

import java.util.List;

public interface PeripheralDeviceDao extends BaseDao {
    List<PeripheralDevice> findAll();

    void remove(long id) throws ClassNotFoundException;

    void remove(PeripheralDevice peripheralDevice);

    void update(long id, Object dto) throws ClassNotFoundException;

    PeripheralDevice findById(long id) throws ClassNotFoundException;
}
