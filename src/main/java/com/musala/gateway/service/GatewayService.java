package com.musala.gateway.service;

import com.musala.gateway.dto.GatewayAddDto;


public interface GatewayService {

    /**
     * prints detailed information about all the gateways
     */
    void printInfoForAllGateways();

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
    void printInfoForAGateway(long id) throws ClassNotFoundException;
}
