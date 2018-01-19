package com.musala.gateway.dao;

import com.musala.gateway.entities.PeripheralDevice;

public interface PeripheralDeviceDao extends BaseDao<PeripheralDevice> {

    void remove(PeripheralDevice peripheralDevice);
}
