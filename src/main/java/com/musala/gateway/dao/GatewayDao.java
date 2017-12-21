package com.musala.gateway.dao;

import com.musala.gateway.entities.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GatewayDao extends JpaRepository<Gateway, String> {
    List<Gateway> findAll();

    Gateway findBySerialNumber(String serialNumber);
}
