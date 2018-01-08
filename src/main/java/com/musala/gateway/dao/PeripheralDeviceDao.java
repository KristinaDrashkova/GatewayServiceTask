package com.musala.gateway.dao;

import com.musala.gateway.entities.PeripheralDevice;

public interface PeripheralDeviceDao extends BaseDao {
    void remove(long id) throws ClassNotFoundException;

    void remove(PeripheralDevice peripheralDevice);

    void update(long id, Object dto) throws ClassNotFoundException;
}
