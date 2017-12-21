package com.musala.gateway.service;

import com.musala.gateway.dao.GatewayDao;
import com.musala.gateway.dao.PeripheralDeviceDao;
import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.entities.PeripheralDevice;
import com.musala.gateway.utils.ModelParser;
import com.musala.gateway.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;

@Service
@Transactional
public class PeripheralDeviceServiceImpl implements PeripheralDeviceService{
    private final PeripheralDeviceDao peripheralDeviceDao;
    private final GatewayDao gatewayDao;

    @Autowired
    public PeripheralDeviceServiceImpl(PeripheralDeviceDao peripheralDeviceDao, GatewayDao gatewayDao) {
        this.peripheralDeviceDao = peripheralDeviceDao;
        this.gatewayDao = gatewayDao;
    }

    public void save(PeripheralDeviceAddDto peripheralDeviceAddDto) {
        PeripheralDevice peripheralDevice = ModelParser.getInstance().map(peripheralDeviceAddDto, PeripheralDevice.class);
        String gatewayNumber = peripheralDeviceAddDto.getGateway();
        Gateway gateway = gatewayDao.findBySerialNumber(gatewayNumber);
        if (gateway.getPeripheralDevices().size() < 10) {
            peripheralDevice.setGateway(gateway);
            if (ValidationUtil.isValid(peripheralDevice)) {
                peripheralDeviceDao.saveAndFlush(peripheralDevice);
            } else {
                //log here
            }
        } else {
            //log here(no more than 10 devices per gateway)
        }
    }

    public void removeDevice(String gatewayNumber, int uid) {
        Gateway gateway = gatewayDao.findBySerialNumber(gatewayNumber);
        gateway.getPeripheralDevices().removeIf(p -> p.getUid() == uid);
    }
}
