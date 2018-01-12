package com.musala.gateway.service;

import com.musala.gateway.dao.GatewayDao;
import com.musala.gateway.dao.PeripheralDeviceDao;
import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.entities.PeripheralDevice;
import com.musala.gateway.entities.Status;
import com.musala.gateway.exceptions.MoreThanTenDevicesException;
import com.musala.gateway.utils.ModelParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PeripheralDeviceServiceImplTest {
    @Autowired
    private PeripheralDeviceService peripheralDeviceService;

    private PeripheralDeviceDao peripheralDeviceDaoMock = Mockito.mock(PeripheralDeviceDao.class);
    private GatewayDao gatewayDaoMock = Mockito.mock(GatewayDao.class);
    private PeripheralDeviceAddDto peripheralDeviceAddDto =
            new PeripheralDeviceAddDto(1, "IBM", new Date(), Status.OFFLINE, 1);
    private PeripheralDeviceAddDto peripheralDeviceAddDtoInvalid =
            new PeripheralDeviceAddDto(null, "IBM", new Date(), Status.OFFLINE, 1);
    private Gateway gatewayMock = Mockito.mock(Gateway.class);
    private PeripheralDevice peripheralDevice = new PeripheralDevice(1, "IBM", new Date(), Status.ONLINE);

    @Before
    public void setUp() throws ParseException, ClassNotFoundException {
        peripheralDeviceService.setPeripheralDeviceDao(peripheralDeviceDaoMock);
        peripheralDeviceService.setGatewayDao(gatewayDaoMock);
        peripheralDevice.setGateway(gatewayMock);
        Mockito.when(gatewayMock.getPeripheralDevices()).thenReturn(new LinkedHashSet<>(1));
        Mockito.when(peripheralDeviceDaoMock.findById(1)).thenReturn(peripheralDevice);
        Mockito.when(gatewayDaoMock.findById(1)).thenReturn(gatewayMock);
        Mockito.when(gatewayMock.getPeripheralDevices()).thenReturn(new LinkedHashSet<>());
    }

    @Test
    public void saveShouldWorkCorrectly() throws Exception {
        peripheralDeviceService.save(peripheralDeviceAddDto);
        PeripheralDevice peripheralDevice = ModelParser.getInstance().map(peripheralDeviceAddDto, PeripheralDevice.class);
        verify(peripheralDeviceDaoMock, times(1)).save(peripheralDevice);
    }

    @Test(expected = NullPointerException.class)
    public void saveShouldThrowExceptionWithNull() throws Exception {
        peripheralDeviceService.save(null);

    }

    @Test
    public void removeDeviceWithIdShouldWorkCorrectly() throws Exception {
        peripheralDeviceService.removeDevice(1);
        Mockito.verify(peripheralDeviceDaoMock, times(1)).remove(1);
    }

    @Test
    public void removeDeviceWithEntityShouldWorkCorrectly() {
        peripheralDeviceService.removeDevice(peripheralDevice);
        Mockito.verify(peripheralDeviceDaoMock, times(1)).remove(peripheralDevice);
    }

    @Test
    public void printInfoForAPeripheralDeviceShouldWorkCorrectly() throws Exception {
        peripheralDeviceService.getPeripheralDevice(1);

        Mockito.verify(peripheralDeviceDaoMock, times(1)).findById(1);
    }

    @Test(expected = MoreThanTenDevicesException.class)
    public void saveMoreThanTenPeripheralDevicesShouldThrowCustomException() throws MoreThanTenDevicesException {
        Set<PeripheralDevice> peripheralDevices = Mockito.mock(LinkedHashSet.class);
        Mockito.when(peripheralDevices.size()).thenReturn(10);
        Mockito.when(gatewayMock.getPeripheralDevices()).thenReturn(peripheralDevices);
        Mockito.when(gatewayDaoMock.findById(1)).thenReturn(gatewayMock);
        peripheralDeviceService.save(peripheralDeviceAddDto);
    }

    @Test
    public void updatePeripheralDeviceShouldWorkCorrectly() throws ClassNotFoundException, MoreThanTenDevicesException {
        peripheralDeviceService.updatePeripheralDevice(1, peripheralDeviceAddDto);
        Assert.assertEquals(peripheralDevice.getUid(), peripheralDeviceAddDto.getUid());
        Assert.assertEquals(peripheralDevice.getVendor(), peripheralDeviceAddDto.getVendor());
        Assert.assertEquals(peripheralDevice.getStatus(), peripheralDeviceAddDto.getStatus());
        Assert.assertEquals(peripheralDevice.getDateCreated(), peripheralDeviceAddDto.getDateCreated());
        Assert.assertEquals(peripheralDevice.getGateway(), gatewayDaoMock.findById(peripheralDeviceAddDto.getGateway()));
    }

    @Test(expected = AssertionError.class)
    public void saveShouldThrowAssertionErrorWithInvalidDto() throws MoreThanTenDevicesException {
        peripheralDeviceService.save(peripheralDeviceAddDtoInvalid);
    }

    @Test(expected = AssertionError.class)
    public void updateShouldThrowAssertionErrorWithInvalidDto() throws MoreThanTenDevicesException {
        peripheralDeviceService.updatePeripheralDevice(1, peripheralDeviceAddDtoInvalid);
    }
}