package com.musala.gateway.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class GatewayUpdateDto {
    @NotNull
    @Expose
    private long id;

    @NotNull
    @Expose
    private GatewayAddDto gatewayAddDto;

    public GatewayUpdateDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GatewayAddDto getGatewayAddDto() {
        return gatewayAddDto;
    }

    public void setGatewayAddDto(GatewayAddDto gatewayAddDto) {
        this.gatewayAddDto = gatewayAddDto;
    }
}
