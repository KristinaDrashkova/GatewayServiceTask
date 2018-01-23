package com.musala.gateway.service;


import com.musala.gateway.dao.GatewayDao;
import com.musala.gateway.dao.PeripheralDeviceDao;
import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.entities.PeripheralDevice;
import com.musala.gateway.exceptions.ModelNotFoundException;
import com.musala.gateway.exceptions.MoreThanTenDevicesException;

public interface PeripheralDeviceService {

    /**
     * maps the dto object to its corresponding entity and saves it to the database
     *
     * @param peripheralDeviceAddDto data transfer object corresponding to an entity
     */
    void savePeripheralDevice(PeripheralDeviceAddDto peripheralDeviceAddDto) throws MoreThanTenDevicesException, ModelNotFoundException;

    /**
     * removes from database the device by a given parameter
     *
     * @param id identifier generated in database
     */
    void removeDevice(long id) throws ModelNotFoundException;

    /**
     * @param id identifier generated in database
     * @return device found in database by given identifier
     */
    PeripheralDevice getPeripheralDevice(long id) throws ModelNotFoundException;

    /**
     * updates peripheralDevice in database
     *
     * @param id                     identifier generated in database
     * @param peripheralDeviceAddDto mapping object for PeripheralDevice
     */
    void updatePeripheralDevice(long id, PeripheralDeviceAddDto peripheralDeviceAddDto) throws MoreThanTenDevicesException, ModelNotFoundException;

    void setGatewayDao(GatewayDao gatewayDao);

    void setPeripheralDeviceDao(PeripheralDeviceDao peripheralDeviceDao);
}
