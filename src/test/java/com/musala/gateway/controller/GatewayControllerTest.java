package com.musala.gateway.controller;

import com.musala.gateway.dto.GatewayAddDto;
import com.musala.gateway.service.GatewayService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GatewayControllerTest {
    private GatewayAddDto gatewayAddDto =
            new GatewayAddDto("1330-1691-2320-1630-3127-2515", "A", "192.168.3.24");

    @Mock
    private GatewayService mockedGatewayService;

    private GatewayController gatewayController;


    @Before
    public void setUp() {
        gatewayController = new GatewayController();
        gatewayController.setGatewayService(mockedGatewayService);
    }

    @Test
    public void updateGatewayShouldWorkCorrectly() throws Exception {
        gatewayController.updateGateway(1, gatewayAddDto);
        verify(mockedGatewayService, times(1)).updateGateway(1, gatewayAddDto);
        verifyNoMoreInteractions(mockedGatewayService);
    }

    @Test
    public void saveGatewayShouldWorkCorrectly() throws Exception {
        gatewayController.saveGateway(gatewayAddDto);
        verify(mockedGatewayService, times(1)).save(gatewayAddDto);
        verifyNoMoreInteractions(mockedGatewayService);
    }

    @Test
    public void getInfoAboutAllGatewaysShouldWorkCorrectly() throws Exception {
        gatewayController.getInfoAboutAllGateways();
        verify(mockedGatewayService, times(1)).getAllGateways();
        verifyNoMoreInteractions(mockedGatewayService);
    }

    @Test
    public void getInfoAboutAGatewayShouldWorkCorrectly() throws Exception {
        gatewayController.getInfoAboutAGateway(1);
        verify(mockedGatewayService, times(1)).getGateway(1);
        verifyNoMoreInteractions(mockedGatewayService);
    }
}