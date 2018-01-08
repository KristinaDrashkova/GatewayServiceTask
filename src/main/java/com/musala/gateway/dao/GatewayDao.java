package com.musala.gateway.dao;

import com.musala.gateway.entities.Gateway;

import java.util.List;

public interface GatewayDao extends BaseDao {
    List<Gateway> findAll();

    Gateway findById(long id) throws ClassNotFoundException;

    void update(long id, Object dto) throws ClassNotFoundException;
}
