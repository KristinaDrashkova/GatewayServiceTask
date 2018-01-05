package com.musala.gateway.dao;

public interface PeripheralDeviceDao extends BaseDao {
    void remove(long id) throws ClassNotFoundException;
}
