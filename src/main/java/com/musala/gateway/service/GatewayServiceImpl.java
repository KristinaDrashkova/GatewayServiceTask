package com.musala.gateway.service;

import com.musala.gateway.dao.GatewayDao;
import com.musala.gateway.dto.GatewayAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.exceptions.ModelNotFoundException;
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
    public Gateway saveGateway(GatewayAddDto gatewayAddDto) {
        assert (gatewayAddDto.getName() != null);
        assert (gatewayAddDto.getIpv4Address() != null);
        assert (gatewayAddDto.getSerialNumber() != null);
        Gateway gateway = ModelParser.getInstance().map(gatewayAddDto, Gateway.class);
        gatewayDao.save(gateway);
        return gateway;
    }

    public Gateway getGateway(long id) throws ModelNotFoundException {
        Gateway gateway = gatewayDao.findById(id);
        if (gateway == null) {
            throw new ModelNotFoundException(id, Gateway.class.getSimpleName());
        }
        return gateway;
    }

    @Transactional
    public void updateGateway(long id, GatewayAddDto gatewayAddDto) throws ModelNotFoundException {
        assert (gatewayAddDto.getName() != null);
        assert (gatewayAddDto.getIpv4Address() != null);
        assert (gatewayAddDto.getSerialNumber() != null);
        Gateway gateway = gatewayDao.findById(id);
        if (gateway == null) {
            throw new ModelNotFoundException(id, Gateway.class.getSimpleName());
        }
        gateway.setName(gatewayAddDto.getName());
        gateway.setSerialNumber(gatewayAddDto.getSerialNumber());
        gateway.setIpv4Address(gatewayAddDto.getIpv4Address());
    }

    public void setGatewayDao(GatewayDao gatewayDao) {
        this.gatewayDao = gatewayDao;
    }
}
