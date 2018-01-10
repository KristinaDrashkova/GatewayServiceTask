package com.musala.gateway.service;

import com.musala.gateway.JpaConfig;
import com.musala.gateway.dao.GatewayDao;
import com.musala.gateway.dto.GatewayAddDto;
import com.musala.gateway.entities.Gateway;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {JpaConfig.class},
        loader = AnnotationConfigContextLoader.class)
public class GatewayServiceImplTest {
    private GatewayDao gatewayDaoMock = Mockito.mock(GatewayDao.class);
    @Autowired
    private GatewayService gatewayService;
    private GatewayAddDto gatewayAddDto = new GatewayAddDto("1330-1691-2320-1630-3127-2515", "A", "192.168.3.24");
    private GatewayAddDto gatewayAddDtoInvalid =
            new GatewayAddDto("1331-1691-2320-1630-3127-2516", "Q", "1921.168.3.24");
    private Gateway gateway = new Gateway("1245-1234-1234-1235", "name", "192.168.3.24");


    @Before
    public void setUp() throws ParseException, ClassNotFoundException {
        gatewayService.setGatewayDao(gatewayDaoMock);
        Mockito.when(gatewayDaoMock.findById(1)).thenReturn(gateway);
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
        Gateway gatewayFromDto = ModelParser.getInstance().map(gatewayAddDto, Gateway.class);
        gatewayService.updateGateway(1, gatewayAddDto);
        verify(gatewayDaoMock, times(1)).update(gateway, gatewayFromDto);
    }
}