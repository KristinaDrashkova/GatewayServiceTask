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
public class PeripheralDeviceServiceImpl implements PeripheralDeviceService {
    @Autowired
    private PeripheralDeviceDao peripheralDeviceDao;
    @Autowired
    private GatewayDao gatewayDao;

    @Transactional
    @Override
    public void save(PeripheralDeviceAddDto peripheralDeviceAddDto) throws MoreThanTenDevicesException {
        assert (peripheralDeviceAddDto.getUid() != null);
        assert (peripheralDeviceAddDto.getVendor() != null);
        assert (peripheralDeviceAddDto.getStatus() != null);
        assert (peripheralDeviceAddDto.getDateCreated() != null);
        long gatewayId = peripheralDeviceAddDto.getGateway();
        Gateway gateway = gatewayDao.findById(gatewayId);
        checkForLessThanTenDevices(gateway);
        PeripheralDevice peripheralDevice = ModelParser.getInstance().map(peripheralDeviceAddDto, PeripheralDevice.class);
        peripheralDevice.setGateway(gateway);
        peripheralDeviceDao.save(peripheralDevice);

    }

    @Transactional
    @Override
    public void removeDevice(long id) {
        peripheralDeviceDao.remove(id);
    }

    @Override
    public void removeDevice(PeripheralDeviceAddDto peripheralDeviceAddDto) {
        PeripheralDevice peripheralDevice = peripheralDeviceDao.findByUid(peripheralDeviceAddDto.getUid());
        peripheralDeviceDao.remove(peripheralDevice);
    }

    @Override
    public PeripheralDevice getPeripheralDevice(long id) {
        return peripheralDeviceDao.findById(id);
    }

    @Transactional
    @Override
    public void updatePeripheralDevice(long id, PeripheralDeviceAddDto peripheralDeviceAddDto) throws MoreThanTenDevicesException {
        assert (peripheralDeviceAddDto.getUid() != null);
        assert (peripheralDeviceAddDto.getVendor() != null);
        assert (peripheralDeviceAddDto.getStatus() != null);
        assert (peripheralDeviceAddDto.getDateCreated() != null);
        Gateway gateway = gatewayDao.findById(peripheralDeviceAddDto.getGateway());
        PeripheralDevice peripheralDevice = peripheralDeviceDao.findById(id);
        if (peripheralDeviceAddDto.getGateway() != peripheralDevice.getGateway().getId()) {
            checkForLessThanTenDevices(gateway);
        }
        peripheralDevice.setUid(peripheralDeviceAddDto.getUid());
        peripheralDevice.setVendor(peripheralDeviceAddDto.getVendor());
        peripheralDevice.setStatus(peripheralDeviceAddDto.getStatus());
        peripheralDevice.setDateCreated(peripheralDeviceAddDto.getDateCreated());
        peripheralDevice.setGateway(gateway);
    }

    public void setPeripheralDeviceDao(PeripheralDeviceDao peripheralDeviceDao) {
        this.peripheralDeviceDao = peripheralDeviceDao;
    }

    public void setGatewayDao(GatewayDao gatewayDao) {
        this.gatewayDao = gatewayDao;
    }

    private void checkForLessThanTenDevices(Gateway gateway) throws MoreThanTenDevicesException {
        if (gateway.getPeripheralDevices().size() >= 10) {
            throw new MoreThanTenDevicesException("Can not add more than 10 devices to a gateway");
        }
    }
}
