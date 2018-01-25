package com.musala.gateway.service;

import com.musala.gateway.dao.GatewayDao;
import com.musala.gateway.dto.GatewayAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.exceptions.ModelNotFoundException;

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
    Gateway saveGateway(GatewayAddDto gatewayAddDto);

    /**
     * returns a gateway by given parameter
     *
     * @param id generated in database
     */
    Gateway getGateway(long id) throws ClassNotFoundException, ModelNotFoundException;

    /**
     * updates gateway record in database
     *
     * @param id            identifier generated in database
     * @param gatewayAddDto mapping object for gateway
     */
    void updateGateway(long id, GatewayAddDto gatewayAddDto) throws ModelNotFoundException;

    void setGatewayDao(GatewayDao gatewayDao);
}
