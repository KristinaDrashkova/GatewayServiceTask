package com.musala.gateway.dao;

import com.musala.gateway.entities.Gateway;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class GatewayDaoImpl extends BaseDaoImpl implements GatewayDao {
    @SuppressWarnings("unchecked")
    @Override
    public List<Gateway> findAll() {
        return super.findAll("Gateway");
    }

    @Override
    public Gateway findById(long id) throws ClassNotFoundException {
        return (Gateway) super.findById(id, "Gateway");
    }
}
