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
        if (baseEntity instanceof Gateway) {
            Gateway gateway = (Gateway) baseEntity;
            if (gateway.getPeripheralDevices().size() < 10) {
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
    }

    @Override
    public void removeDevice(long id) {
        peripheralDeviceDao.remove(id);
    }

    @Override
    public void printInfoForAPeripheralDevice(long id) {
        BaseEntity baseEntity = peripheralDeviceDao.findById(id);
        if (baseEntity instanceof PeripheralDevice) {
            PeripheralDevice peripheralDevice = (PeripheralDevice) baseEntity;
            System.out.println(peripheralDevice);
        }
    }
}
