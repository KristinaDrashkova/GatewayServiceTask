package com.musala.gateway.dao;

import com.musala.gateway.entities.Gateway;
import org.springframework.stereotype.Repository;


@Repository
public class GatewayDaoImpl extends BaseDaoImpl<Gateway> implements GatewayDao {
    public GatewayDaoImpl() {
        super(Gateway.class);
    }

}
