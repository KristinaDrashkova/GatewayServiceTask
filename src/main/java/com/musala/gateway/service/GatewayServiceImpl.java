package com.musala.gateway.service;

import com.musala.gateway.dao.GatewayDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class GatewayServiceImpl implements GatewayService {
    private final GatewayDao gatewayDao;

    @Autowired
    public GatewayServiceImpl(GatewayDao gatewayDao) {
        this.gatewayDao = gatewayDao;
    }

    @Override
    public void create() {

    }

    @Override
    public void read() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
