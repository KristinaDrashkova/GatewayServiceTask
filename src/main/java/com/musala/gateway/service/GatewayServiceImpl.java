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
    private final GatewayDao gatewayDao;

    @Autowired
    public GatewayServiceImpl(GatewayDao gatewayDao) {
        this.gatewayDao = gatewayDao;
    }

    @SuppressWarnings("unchecked")
    public void printInfoForAllGateways() {
        List<Gateway> gateways = gatewayDao.findAll();
        for (Gateway gateway : gateways) {
            System.out.println(gateway.toString());
        }
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

    public void printInfoForAGateway(long id) throws ClassNotFoundException {
        Gateway gateway = (Gateway) gatewayDao.findById(id, "com.musala.gateway.entities.Gateway");
        System.out.println(gateway);
    }
}
