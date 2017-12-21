package com.musala.gateway.service;

import com.musala.gateway.dto.GatewayAddDto;


public interface GatewayService {
    void printInfoForAllGateways();

    void save(GatewayAddDto gatewayAddDto);

    void printInfoForAGateway(String serialNumber);
}
