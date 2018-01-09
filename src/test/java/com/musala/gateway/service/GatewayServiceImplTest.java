package com.musala.gateway.service;

import com.musala.gateway.JpaConfig;
import com.musala.gateway.dao.GatewayDao;
import com.musala.gateway.dto.GatewayAddDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.text.ParseException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {JpaConfig.class},
        loader = AnnotationConfigContextLoader.class)
public class GatewayServiceImplTest {
    private GatewayDao gatewayDaoMock;
    @Autowired
    private GatewayService gatewayService;
    private GatewayAddDto gatewayAddDto;


    @Before
    public void setUp() throws ParseException {
        gatewayDaoMock = Mockito.mock(GatewayDao.class);
        gatewayService.setGatewayDao(gatewayDaoMock);
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
        verify(gatewayDaoMock, times(1)).save(any());
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