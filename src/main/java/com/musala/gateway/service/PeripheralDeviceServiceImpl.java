package com.musala.gateway.service;

import com.musala.gateway.dao.GatewayDao;
import com.musala.gateway.dao.PeripheralDeviceDao;
import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.entities.PeripheralDevice;
import com.musala.gateway.exceptions.MoreThanTenDevicesException;
import com.musala.gateway.utils.ModelParser;
import com.musala.gateway.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service

public class PeripheralDeviceServiceImpl implements PeripheralDeviceService {
    @Autowired
    private PeripheralDeviceDao peripheralDeviceDao;
    @Autowired
    private GatewayDao gatewayDao;

    public PeripheralDeviceServiceImpl() {
        this.setGatewayDao(gatewayDao);
        this.setPeripheralDeviceDao(peripheralDeviceDao);
    }

    @Transactional
    @Override
    public void save(PeripheralDeviceAddDto peripheralDeviceAddDto) throws ClassNotFoundException, MoreThanTenDevicesException {
        Integer gatewayId = peripheralDeviceAddDto.getGateway();
        Gateway gateway = gatewayDao.findById(gatewayId);
        if (gateway.getPeripheralDevices().size() < 10) {
            PeripheralDevice peripheralDevice = ModelParser.getInstance().map(peripheralDeviceAddDto, PeripheralDevice.class);
            peripheralDevice.setGateway(gateway);
            if (ValidationUtil.isValid(peripheralDevice)) {
                peripheralDeviceDao.save(peripheralDevice);
            } else {
                //log here
            }

        } else {
            throw new MoreThanTenDevicesException("Can not add more than 10 devices to a gateway");

        }
    }

    @Transactional
    @Override
    public void removeDevice(long id) throws ClassNotFoundException {
        peripheralDeviceDao.remove(id);
    }

    @Override
    public PeripheralDevice getPeripheralDevice(long id) throws ClassNotFoundException {
        return peripheralDeviceDao.findById(id);
    }

    @Transactional
    @Override
    public void updatePeripheralDevice(long id, PeripheralDeviceAddDto peripheralDeviceAddDto) throws ClassNotFoundException {
        peripheralDeviceDao.update(id, peripheralDeviceAddDto);
    }

    public PeripheralDeviceDao getPeripheralDeviceDao() {
        return peripheralDeviceDao;
    }

    public void setPeripheralDeviceDao(PeripheralDeviceDao peripheralDeviceDao) {
        this.peripheralDeviceDao = peripheralDeviceDao;
    }

    public GatewayDao getGatewayDao() {
        return gatewayDao;
    }

    public void setGatewayDao(GatewayDao gatewayDao) {
        this.gatewayDao = gatewayDao;
    }
}
