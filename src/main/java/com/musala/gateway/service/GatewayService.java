package com.musala.gateway.service;

import com.musala.gateway.dto.GatewayAddDto;
import com.musala.gateway.entities.Gateway;

import java.util.List;


public interface GatewayService {

    /**
     * prints detailed information about all the gateways
     */
    List<Gateway> getAllGateways();

    /**
     * maps the dto object to its corresponding entity and saves it to the database
     *
     * @param gatewayAddDto data transfer object corresponding to an entity
     */
    void save(GatewayAddDto gatewayAddDto);

    /**
     * prints detailed information about a gateway by given parameter
     *
     * @param id generated in database
     */
    Gateway getGateway(long id) throws ClassNotFoundException;
}
