package com.musala.gateway.service;


import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.entities.PeripheralDevice;

public interface PeripheralDeviceService {

    /**
     * maps the dto object to its corresponding entity and saves it to the database
     *
     * @param peripheralDeviceAddDto data transfer object corresponding to an entity
     */
    void save(PeripheralDeviceAddDto peripheralDeviceAddDto) throws ClassNotFoundException;

    /**
     * removes from database the device by a given parameter
     *
     * @param id generated in database
     */
    void removeDevice(long id) throws ClassNotFoundException;

    /**
     * prints detailed information about a peripheral device by given parameter
     *
     * @param id generated in database
     */
    PeripheralDevice getPeripheralDevice(long id) throws ClassNotFoundException;
}
