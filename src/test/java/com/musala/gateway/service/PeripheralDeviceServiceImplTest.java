package com.musala.gateway.service;

import com.musala.gateway.JpaConfig;
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
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchekced")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {JpaConfig.class},
        loader = AnnotationConfigContextLoader.class)
public class PeripheralDeviceServiceImplTest {
    private PeripheralDeviceDao peripheralDeviceDaoMock = Mockito.mock(PeripheralDeviceDao.class);
    private GatewayDao gatewayDaoMock = Mockito.mock(GatewayDao.class);
    @Autowired
    private PeripheralDeviceService peripheralDeviceService;
    private PeripheralDeviceAddDto peripheralDeviceAddDto =
            new PeripheralDeviceAddDto(1, "IBM", new Date(), Status.OFFLINE, 1);
    private Gateway gatewayMock = Mockito.mock(Gateway.class);
    private PeripheralDevice peripheralDevice = new PeripheralDevice(1, "IBM", new Date(), Status.ONLINE);

    @Before
    public void setUp() throws ParseException, ClassNotFoundException {
        peripheralDeviceService.setPeripheralDeviceDao(peripheralDeviceDaoMock);
        peripheralDeviceService.setGatewayDao(gatewayDaoMock);
        Mockito.when(gatewayMock.getPeripheralDevices()).thenReturn(new LinkedHashSet<>(1));
        Mockito.when(peripheralDeviceDaoMock.findById(1)).thenReturn(peripheralDevice);
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
    public void removeDeviceWithIdShouldWorkCorrectly() throws Exception {
        peripheralDeviceService.removeDevice(1);
        Mockito.verify(peripheralDeviceDaoMock, times(1)).remove(1);
    }

    @Test
    public void removeDeviceWithEntityShouldWorkCorrectly() throws ClassNotFoundException {
        peripheralDeviceService.removeDevice(peripheralDevice);
        Mockito.verify(peripheralDeviceDaoMock, times(1)).remove(peripheralDevice);
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
        PeripheralDevice peripheralDeviceFromDto = ModelParser.getInstance().map(peripheralDeviceAddDto, PeripheralDevice.class);
        verify(peripheralDeviceDaoMock, times(1)).update(peripheralDevice, peripheralDeviceFromDto);
    }
}