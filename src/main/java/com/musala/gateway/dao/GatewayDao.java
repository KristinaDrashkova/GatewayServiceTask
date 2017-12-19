package com.musala.gateway.dao;

import com.musala.gateway.entities.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatewayDao extends JpaRepository<Gateway, String>{
}
