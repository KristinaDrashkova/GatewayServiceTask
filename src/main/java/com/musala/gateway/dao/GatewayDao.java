package com.musala.gateway.dao;

import com.musala.gateway.entities.Gateway;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GatewayDao  {
    List<Gateway> findAll();

    Gateway findById(Integer id);

    void save(Gateway gateway);
}
