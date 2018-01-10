package com.musala.gateway.dao;

import com.musala.gateway.entities.Gateway;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class GatewayDaoImpl extends BaseDaoImpl implements GatewayDao {

    @Override
    public List<Gateway> findAll() {
        return super.findAll(Gateway.class);
    }

    @Override
    public Gateway findById(long id) throws ClassNotFoundException {
        return (Gateway) super.findById(id, Gateway.class);
    }
}
