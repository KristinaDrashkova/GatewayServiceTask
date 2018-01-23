package com.musala.gateway.service;

import com.musala.gateway.dao.GatewayDao;
import com.musala.gateway.dao.PeripheralDeviceDao;
import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.entities.PeripheralDevice;
import com.musala.gateway.exceptions.ModelNotFoundException;
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
    public void savePeripheralDevice(PeripheralDeviceAddDto peripheralDeviceAddDto) throws MoreThanTenDevicesException, ModelNotFoundException {
        assert (peripheralDeviceAddDto.getUid() != null);
        assert (peripheralDeviceAddDto.getVendor() != null);
        assert (peripheralDeviceAddDto.getStatus() != null);
        assert (peripheralDeviceAddDto.getDateCreated() != null);
        long gatewayId = peripheralDeviceAddDto.getGateway();
        Gateway gateway = gatewayDao.findById(gatewayId);
        if (gateway == null) {
            throw new ModelNotFoundException(gatewayId, Gateway.class.getSimpleName());
        }
        checkForLessThanTenDevices(gateway);
        PeripheralDevice peripheralDevice = ModelParser.getInstance().map(peripheralDeviceAddDto, PeripheralDevice.class);
        peripheralDevice.setGateway(gateway);
        peripheralDeviceDao.save(peripheralDevice);

    }

    @Transactional
    @Override
    public void removeDevice(long id) throws ModelNotFoundException {
        PeripheralDevice peripheralDevice = peripheralDeviceDao.findById(id);
        if (peripheralDevice == null) {
            throw new ModelNotFoundException(id, PeripheralDevice.class.getSimpleName());
        }
        peripheralDeviceDao.remove(peripheralDevice);
    }

    @Override
    public PeripheralDevice getPeripheralDevice(long id) throws ModelNotFoundException {
        PeripheralDevice peripheralDevice = peripheralDeviceDao.findById(id);
        if (peripheralDevice == null) {
            throw new ModelNotFoundException(id, PeripheralDevice.class.getSimpleName());
        }
        return peripheralDevice;
    }

    @Transactional
    @Override
    public void updatePeripheralDevice(long id, PeripheralDeviceAddDto peripheralDeviceAddDto) throws MoreThanTenDevicesException, ModelNotFoundException {
        assert (peripheralDeviceAddDto.getUid() != null);
        assert (peripheralDeviceAddDto.getVendor() != null);
        assert (peripheralDeviceAddDto.getStatus() != null);
        assert (peripheralDeviceAddDto.getDateCreated() != null);
        Gateway gateway = gatewayDao.findById(peripheralDeviceAddDto.getGateway());
        if (gateway == null) {
            throw new ModelNotFoundException(peripheralDeviceAddDto.getGateway(), Gateway.class.getSimpleName());
        }
        PeripheralDevice peripheralDevice = peripheralDeviceDao.findById(id);
        if (peripheralDevice == null) {
            throw new ModelNotFoundException(id, PeripheralDevice.class.getSimpleName());
        }
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
