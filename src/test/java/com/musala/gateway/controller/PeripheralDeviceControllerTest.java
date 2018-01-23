package com.musala.gateway.controller;

import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.entities.Status;
import com.musala.gateway.service.PeripheralDeviceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PeripheralDeviceControllerTest {
    private PeripheralDeviceAddDto peripheralDeviceAddDto =
            new PeripheralDeviceAddDto(1, "IBM", new Date(), Status.OFFLINE, 1);

    @Mock
    private PeripheralDeviceService mockedPeripheralDeviceService;

    private PeripheralDeviceController peripheralDeviceController;

    @Before
    public void setUp() {
        peripheralDeviceController = new PeripheralDeviceController();
        peripheralDeviceController.setPeripheralDeviceService(mockedPeripheralDeviceService);
    }

    @Test
    public void updatePeripheralDevice() throws Exception {
        peripheralDeviceController.updatePeripheralDevice(1, peripheralDeviceAddDto);
        verify(mockedPeripheralDeviceService, times(1)).updatePeripheralDevice(1, peripheralDeviceAddDto);
        verifyNoMoreInteractions(mockedPeripheralDeviceService);
    }

    @Test
    public void savePeripheralDevice() throws Exception {
        peripheralDeviceController.savePeripheralDevice(peripheralDeviceAddDto);
        verify(mockedPeripheralDeviceService, times(1)).savePeripheralDevice(peripheralDeviceAddDto);
        verifyNoMoreInteractions(mockedPeripheralDeviceService);
    }

    @Test
    public void removePeripheralDevice() throws Exception {
        peripheralDeviceController.removePeripheralDevice(1);
        verify(mockedPeripheralDeviceService, times(1)).removeDevice(1);
        verifyNoMoreInteractions(mockedPeripheralDeviceService);
    }

}