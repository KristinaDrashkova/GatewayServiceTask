package com.musala.gateway.service;

import com.musala.gateway.dao.GatewayDao;
import com.musala.gateway.dao.PeripheralDeviceDao;
import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.entities.BaseEntity;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.entities.PeripheralDevice;
import com.musala.gateway.utils.ModelParser;
import com.musala.gateway.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PeripheralDeviceServiceImpl implements PeripheralDeviceService {
    private final PeripheralDeviceDao peripheralDeviceDao;
    private final GatewayDao gatewayDao;

    @Autowired
    public PeripheralDeviceServiceImpl(PeripheralDeviceDao peripheralDeviceDao, GatewayDao gatewayDao) {
        this.peripheralDeviceDao = peripheralDeviceDao;
        this.gatewayDao = gatewayDao;
    }


    @Override
    public void save(PeripheralDeviceAddDto peripheralDeviceAddDto) {
        PeripheralDevice peripheralDevice = ModelParser.getInstance().map(peripheralDeviceAddDto, PeripheralDevice.class);
        Integer gatewayId = peripheralDeviceAddDto.getGateway();
        BaseEntity baseEntity = gatewayDao.findById(gatewayId);
        Gateway gateway = (Gateway) baseEntity;
        if (gateway != null && gateway.getPeripheralDevices().size() < 10) {
            peripheralDevice.setGateway(gateway);
            if (ValidationUtil.isValid(peripheralDevice)) {
                peripheralDeviceDao.save(peripheralDevice);
            } else {
                //log here
            }
        } else {
            //log here(no more than 10 devices per gateway)
        }
    }

    @Override
    public void removeDevice(int uid) {
        peripheralDeviceDao.remove(uid);
    }

    @Override
    public void printInfoForAPeripheralDevice(int uid) {
        BaseEntity baseEntity = peripheralDeviceDao.findById(uid);
        PeripheralDevice peripheralDevice = (PeripheralDevice) baseEntity;
        System.out.println(peripheralDevice);
    }
}
