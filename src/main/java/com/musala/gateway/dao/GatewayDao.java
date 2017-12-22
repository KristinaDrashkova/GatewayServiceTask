package com.musala.gateway.dao;

import com.musala.gateway.entities.Gateway;

import java.util.List;

public interface GatewayDao {
    List<Gateway> findAll();

    Gateway findById(Integer id);

    void save(Gateway gateway);
}
