package com.musala.gateway.service;

import com.musala.gateway.dao.PeripheralDeviceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PeripheralDeviceServiceImpl {
    private final PeripheralDeviceDao peripheralDeviceDao;

    @Autowired
    public PeripheralDeviceServiceImpl(PeripheralDeviceDao peripheralDeviceDao) {
        this.peripheralDeviceDao = peripheralDeviceDao;
    }
}
