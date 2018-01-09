package com.musala.gateway.service;

import com.musala.gateway.dao.GatewayDao;
import com.musala.gateway.dao.PeripheralDeviceDao;
import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.entities.PeripheralDevice;
import com.musala.gateway.entities.Status;
import com.musala.gateway.exceptions.MoreThanTenDevicesException;
import com.musala.gateway.utils.ModelParser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PeripheralDeviceServiceImplTest {
    private PeripheralDeviceDao peripheralDeviceDaoMock;
    private GatewayDao gatewayDaoMock;
    private PeripheralDeviceService peripheralDeviceService;
    private PeripheralDeviceAddDto peripheralDeviceAddDto;
    private Gateway gatewayMock;

    @Before
    public void setUp() throws ParseException {
        peripheralDeviceDaoMock = Mockito.mock(PeripheralDeviceDao.class);
        gatewayDaoMock = Mockito.mock(GatewayDao.class);
        peripheralDeviceService = new PeripheralDeviceServiceImpl(peripheralDeviceDaoMock, gatewayDaoMock);
        peripheralDeviceAddDto =
                new PeripheralDeviceAddDto(1, "IBM", new Date(), Status.OFFLINE, 1);
        gatewayMock = Mockito.mock(Gateway.class);
        Mockito.when(gatewayMock.getPeripheralDevices()).thenReturn(new LinkedHashSet<>(1));
    }

    @Test
    public void saveShouldWorkCorrectly() throws Exception {
        Mockito.when(gatewayDaoMock.findById(1)).thenReturn(gatewayMock);
        peripheralDeviceService.save(peripheralDeviceAddDto);
        PeripheralDevice peripheralDevice = ModelParser.getInstance().map(peripheralDeviceAddDto, PeripheralDevice.class);
        verify(peripheralDeviceDaoMock, times(1)).save(peripheralDevice);
    }

    @Test(expected = NullPointerException.class)
    public void saveShouldThrowExceptionWithNull() throws Exception {
        peripheralDeviceService.save(null);

    }

    @Test
    public void removeDeviceShouldWorkCorrectly() throws Exception {
        peripheralDeviceService.removeDevice(1);

        Mockito.verify(peripheralDeviceDaoMock, times(1)).remove(1);
    }

    @Test
    public void printInfoForAPeripheralDeviceShouldWorkCorrectly() throws Exception {
        peripheralDeviceService.getPeripheralDevice(1);

        Mockito.verify(peripheralDeviceDaoMock, times(1)).findById(1);
    }

    @Test(expected = MoreThanTenDevicesException.class)
    public void saveMoreThanTenPeripheralDevicesShouldThrowCustomException() throws ClassNotFoundException, MoreThanTenDevicesException {
        Set<PeripheralDevice> peripheralDevices = Mockito.mock(LinkedHashSet.class);
        Mockito.when(peripheralDevices.size()).thenReturn(10);
        Mockito.when(gatewayMock.getPeripheralDevices()).thenReturn(peripheralDevices);
        Mockito.when(gatewayDaoMock.findById(1)).thenReturn(gatewayMock);
        peripheralDeviceService.save(peripheralDeviceAddDto);
    }

    @Test
    public void updatePeripheralDeviceShouldWorkCorrectly() throws ClassNotFoundException {
        peripheralDeviceService.updatePeripheralDevice(1, peripheralDeviceAddDto);
        verify(peripheralDeviceDaoMock, times(1)).update(1, peripheralDeviceAddDto);
    }
}