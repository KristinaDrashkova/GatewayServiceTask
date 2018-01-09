package com.musala.gateway.service;

import com.musala.gateway.dao.GatewayDao;
import com.musala.gateway.dto.GatewayAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.utils.ModelParser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class GatewayServiceImplTest {
    private GatewayDao gatewayDaoMock;
    private GatewayService gatewayService;
    private GatewayAddDto gatewayAddDto;


    @Before
    public void setUp() throws ParseException {
        gatewayDaoMock = Mockito.mock(GatewayDao.class);
        gatewayService = new GatewayServiceImpl(gatewayDaoMock);
        gatewayAddDto = new GatewayAddDto("1330-1691-2320-1630-3127-2515", "A", "192.168.3.24");
    }

    @Test
    public void printInfoForAllGatewaysShouldWorkCorrectly() throws Exception {
        gatewayService.getAllGateways();
        verify(gatewayDaoMock, times(1)).findAll();
    }

    @Test
    public void saveShouldWorkCorrectly() throws Exception {
        gatewayService.save(gatewayAddDto);
        Gateway gateway = ModelParser.getInstance().map(gatewayAddDto, Gateway.class);
        verify(gatewayDaoMock, times(1)).save(gateway);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowExceptionWithNullArgument() throws Exception {
        gatewayService.save(null);
    }

    @Test
    public void printInfoForAGatewayShouldWorkCorrectly() throws Exception {
        gatewayService.getGateway(1);
        verify(gatewayDaoMock, times(1)).findById(1);
    }

    @Test
    public void updateShouldWorkCorrectly() throws ClassNotFoundException {
        gatewayService.updateGateway(1, gatewayAddDto);
        verify(gatewayDaoMock, times(1)).update(1, gatewayAddDto);
    }
}