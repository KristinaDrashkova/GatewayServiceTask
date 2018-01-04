package com.musala.gateway.service;

import com.musala.gateway.dao.GatewayDao;
import com.musala.gateway.dto.GatewayAddDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class GatewayServiceImplTest {
    private GatewayDao gatewayDaoMock;
    private GatewayService gatewayService;
    private GatewayAddDto gatewayAddDto;


    @Before
    public void initialize() {
        gatewayDaoMock = Mockito.mock(GatewayDao.class);
        gatewayService = new GatewayServiceImpl(gatewayDaoMock);
        gatewayAddDto = new GatewayAddDto();
        gatewayAddDto.setIpv4Address("192.168.3.24");
        gatewayAddDto.setName("A");
        gatewayAddDto.setSerialNumber("1330-1691-2320-1630-3127-2515");
    }

    @Test
    public void printInfoForAllGatewaysShouldWorkCorrectly() throws Exception {
        gatewayService.printInfoForAllGateways();
        verify(gatewayDaoMock, times(1)).findAll();
    }

    @Test
    public void saveShouldWorkCorrectly() throws Exception {
        gatewayService.save(gatewayAddDto);
        verify(gatewayDaoMock, times(1)).save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowExceptionWithNullArgument() throws Exception {
        gatewayService.save(null);
    }

    @Test
    public void printInfoForAGatewayShouldWorkCorrectly() throws Exception {
        gatewayService.printInfoForAGateway(1);
        verify(gatewayDaoMock, times(1)).findById(1);
    }

}