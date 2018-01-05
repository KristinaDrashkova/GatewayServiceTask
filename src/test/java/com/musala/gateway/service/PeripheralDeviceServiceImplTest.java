package com.musala.gateway.service;

import com.musala.gateway.dao.GatewayDao;
import com.musala.gateway.dao.PeripheralDeviceDao;
import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.entities.Status;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.Locale;

import static org.mockito.Matchers.any;
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
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        peripheralDeviceAddDto =
                new PeripheralDeviceAddDto(1, "IBM", format.parse("2015-12-20"), Status.OFFLINE, 1);
        gatewayMock = Mockito.mock(Gateway.class);
        Mockito.when(gatewayMock.getPeripheralDevices()).thenReturn(new LinkedHashSet<>(1));
    }

    @Test
    public void saveShouldWorkCorrectly() throws Exception {
        Mockito.when(gatewayDaoMock.findById(1)).thenReturn(gatewayMock);
        peripheralDeviceService.save(peripheralDeviceAddDto);
        verify(peripheralDeviceDaoMock, times(1)).save(any());
    }

    @Test(expected = IllegalArgumentException.class)
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
        peripheralDeviceService.printInfoForAPeripheralDevice(1);

        Mockito.verify(peripheralDeviceDaoMock, times(1)).findById(1);
    }
}