package com.musala.gateway.service;

import com.musala.gateway.dao.GatewayDao;
import com.musala.gateway.dao.PeripheralDeviceDao;
import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.entities.PeripheralDevice;
import com.musala.gateway.exceptions.MoreThanTenDevicesException;
import com.musala.gateway.utils.ModelParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@SuppressWarnings("unchecked")
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
    public void save(PeripheralDeviceAddDto peripheralDeviceAddDto) throws MoreThanTenDevicesException {
        assert (peripheralDeviceAddDto.getUid() != null && peripheralDeviceAddDto.getVendor() != null && peripheralDeviceAddDto.getStatus() != null
        && peripheralDeviceAddDto.getDateCreated() != null && peripheralDeviceAddDto.getGateway() != null);
        Integer gatewayId = peripheralDeviceAddDto.getGateway();
        Gateway gateway = gatewayDao.findById(gatewayId);
        if (gateway.getPeripheralDevices().size() < 10) {
            PeripheralDevice peripheralDevice = ModelParser.getInstance().map(peripheralDeviceAddDto, PeripheralDevice.class);
            peripheralDevice.setGateway(gateway);
            peripheralDeviceDao.save(peripheralDevice);

        } else {
            throw new MoreThanTenDevicesException("Can not add more than 10 devices to a gateway");

        }
    }

    @Transactional
    @Override
    public void removeDevice(long id) {
        peripheralDeviceDao.remove(id);
    }

    @Override
    public void removeDevice(PeripheralDevice peripheralDevice) {
        peripheralDeviceDao.remove(peripheralDevice);
    }

    @Override
    public PeripheralDevice getPeripheralDevice(long id) {
        return peripheralDeviceDao.findById(id);
    }

    @Transactional
    @Override
    public void updatePeripheralDevice(long id, PeripheralDeviceAddDto peripheralDeviceAddDto) {
        assert (peripheralDeviceAddDto.getUid() != null && peripheralDeviceAddDto.getVendor() != null && peripheralDeviceAddDto.getStatus() != null
                && peripheralDeviceAddDto.getDateCreated() != null && peripheralDeviceAddDto.getGateway() != null);
        PeripheralDevice peripheralDevice = peripheralDeviceDao.findById(id);
        PeripheralDevice peripheralDeviceFromDto = ModelParser.getInstance().map(peripheralDeviceAddDto, PeripheralDevice.class);
        peripheralDeviceDao.update(peripheralDevice, peripheralDeviceFromDto);
    }

    public void setPeripheralDeviceDao(PeripheralDeviceDao peripheralDeviceDao) {
        this.peripheralDeviceDao = peripheralDeviceDao;
    }

    public void setGatewayDao(GatewayDao gatewayDao) {
        this.gatewayDao = gatewayDao;
    }
}
