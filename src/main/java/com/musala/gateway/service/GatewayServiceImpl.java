package com.musala.gateway.service;

import com.musala.gateway.dao.GatewayDao;
import com.musala.gateway.dto.GatewayAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.utils.ModelParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class GatewayServiceImpl implements GatewayService {
    @Autowired
    private GatewayDao gatewayDao;

    public List<Gateway> getAllGateways() {
        return gatewayDao.findAll();
    }

    @Transactional
    public void save(GatewayAddDto gatewayAddDto) {
        assert (gatewayAddDto.getName() != null);
        assert (gatewayAddDto.getIpv4Address() != null);
        assert (gatewayAddDto.getSerialNumber() != null);
        Gateway gateway = ModelParser.getInstance().map(gatewayAddDto, Gateway.class);
        gatewayDao.save(gateway);
    }

    public Gateway getGateway(long id) {
        return gatewayDao.findById(id);
    }

    @Transactional
    public void updateGateway(long id, GatewayAddDto gatewayAddDto) {
        assert (gatewayAddDto.getName() != null);
        assert (gatewayAddDto.getIpv4Address() != null);
        assert (gatewayAddDto.getSerialNumber() != null);
        Gateway gateway = gatewayDao.findById(id);
        //TODO remove ModelParser. use setters to update new values for the existing gateway
        gateway.setName(gatewayAddDto.getName());
        gateway.setSerialNumber(gatewayAddDto.getSerialNumber());
        gateway.setIpv4Address(gateway.getIpv4Address());
    }

    public void setGatewayDao(GatewayDao gatewayDao) {
        this.gatewayDao = gatewayDao;
    }
}
