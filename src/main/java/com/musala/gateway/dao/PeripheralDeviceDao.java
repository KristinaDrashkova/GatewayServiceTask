package com.musala.gateway.dao;

import com.musala.gateway.entities.PeripheralDevice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeripheralDeviceDao {
    List<PeripheralDevice> findAll();

    PeripheralDevice findByUid(int uid);

    void save(PeripheralDevice peripheralDevice);

    void remove(int uid);
}
