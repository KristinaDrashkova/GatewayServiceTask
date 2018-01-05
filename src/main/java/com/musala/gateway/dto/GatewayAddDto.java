package com.musala.gateway.dto;

import com.google.gson.annotations.Expose;

public class GatewayAddDto {
    @Expose
    private String serialNumber;

    @Expose
    private String name;

    @Expose
    private String ipv4Address;

    public GatewayAddDto() {
    }

    public GatewayAddDto(String serialNumber, String name, String ipv4Address) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.ipv4Address = ipv4Address;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpv4Address() {
        return ipv4Address;
    }

    public void setIpv4Address(String ipv4Address) {
        this.ipv4Address = ipv4Address;
    }
}
