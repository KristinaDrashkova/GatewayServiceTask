package com.musala.gateway.service;

import com.musala.gateway.dao.GatewayDao;
import com.musala.gateway.dto.GatewayAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.utils.ModelParser;
import com.musala.gateway.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;


@Service
public class GatewayServiceImpl implements GatewayService {
    @Autowired
    private GatewayDao gatewayDao;

//    @Autowired
    public GatewayServiceImpl() {
        this.setGatewayDao(gatewayDao);
    }

    @SuppressWarnings("unchecked")
    public List<Gateway> getAllGateways() {
        return gatewayDao.findAll();
    }

    @Transactional
    public void save(GatewayAddDto gatewayAddDto) {
        Gateway gateway = ModelParser.getInstance().map(gatewayAddDto, Gateway.class);
        gateway.setPeripheralDevices(new HashSet<>());
        if (ValidationUtil.isValid(gateway)) {
            gatewayDao.save(gateway);
        } else {
            //log here
        }
    }

    public Gateway getGateway(long id) throws ClassNotFoundException {
        return gatewayDao.findById(id);
    }

    @Transactional
    public void updateGateway(long id, GatewayAddDto gatewayAddDto) throws ClassNotFoundException {
        Gateway gateway = gatewayDao.findById(id);
        Gateway gatewayFromDto = ModelParser.getInstance().map(gatewayAddDto, Gateway.class);
        if (ValidationUtil.isValid(gatewayFromDto)) {
            gatewayDao.update(gateway, gatewayFromDto);
        }
    }

    public GatewayDao getGatewayDao() {
        return gatewayDao;
    }

    public void setGatewayDao(GatewayDao gatewayDao) {
        this.gatewayDao = gatewayDao;
    }
}
