package com.musala.gateway.service;


import com.musala.gateway.dto.PeripheralDeviceAddDto;

public interface PeripheralDeviceService {
    void save(PeripheralDeviceAddDto peripheralDeviceAddDto);

    void removeDevice(int uid);
}
