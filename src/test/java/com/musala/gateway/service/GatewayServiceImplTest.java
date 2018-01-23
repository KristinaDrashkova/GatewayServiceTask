package com.musala.gateway.service;

import com.musala.gateway.dao.GatewayDao;
import com.musala.gateway.dto.GatewayAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.exceptions.ModelNotFoundException;
import com.musala.gateway.utils.ModelParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GatewayServiceImplTest {
    @Autowired
    private GatewayService gatewayService;

    private GatewayDao gatewayDaoMock = Mockito.mock(GatewayDao.class);
    private GatewayAddDto gatewayAddDto = new GatewayAddDto("1330-1691-2320-1630-3127-2515", "A", "192.168.3.24");
    private GatewayAddDto gatewayAddDtoInvalid =
            new GatewayAddDto(null, "Q", "1921.168.3.24");
    private Gateway gateway = new Gateway("1245-1234-1234-1235", "name", "192.168.3.24");


    @Before
    public void setUp() {
        gatewayService.setGatewayDao(gatewayDaoMock);
        Mockito.when(gatewayDaoMock.findById(1)).thenReturn(gateway);
        Mockito.when(gatewayDaoMock.findById(2)).thenReturn(null);
    }

    @Test
    public void printInfoForAllGatewaysShouldWorkCorrectly() throws Exception {
        gatewayService.getAllGateways();
        verify(gatewayDaoMock, times(1)).findAll();
    }

    @Test
    public void saveShouldWorkCorrectly() throws Exception {
        Gateway gateway = ModelParser.getInstance().map(gatewayAddDto, Gateway.class);
        gatewayService.saveGateway(gatewayAddDto);
        verify(gatewayDaoMock, times(1)).save(gateway);
    }

    @Test
    public void printInfoForAGatewayShouldWorkCorrectly() throws Exception {
        gatewayService.getGateway(1);
        verify(gatewayDaoMock, times(1)).findById(1);
    }

    @Test
    public void updateShouldWorkCorrectly() throws ModelNotFoundException {
        gatewayService.updateGateway(1, gatewayAddDto);
        Assert.assertEquals(gateway.getName(), gatewayAddDto.getName());
        Assert.assertEquals(gateway.getIpv4Address(), gatewayAddDto.getIpv4Address());
        Assert.assertEquals(gateway.getSerialNumber(), gatewayAddDto.getSerialNumber());
        verify(gatewayDaoMock, times(1)).findById(1);
    }

    @Test(expected = AssertionError.class)
    public void saveShouldThrowAssertionErrorWithInvalidDto() throws Exception {
        gatewayService.saveGateway(gatewayAddDtoInvalid);
    }

    @Test(expected = AssertionError.class)
    public void updateShouldThrowAssertionErrorWithInvalidDto() throws ModelNotFoundException {
        gatewayService.updateGateway(1, gatewayAddDtoInvalid);
    }

    @Test(expected = ModelNotFoundException.class)
    public void getShouldThrowCustomExceptionWithInvalidId() throws ClassNotFoundException, ModelNotFoundException {
        gatewayService.getGateway(2);
    }

    @Test(expected = ModelNotFoundException.class)
    public void updateShouldThrowCustomExceptionWithInvalidId() throws ModelNotFoundException {
        gatewayService.updateGateway(2, gatewayAddDto);
    }
}