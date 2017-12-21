package com.musala.gateway.dto;

import com.google.gson.annotations.Expose;
import com.musala.gateway.entities.Status;

import java.time.LocalDate;

public class PeripheralDeviceAddDto {
    private Integer uid;

    @Expose
    private String vendor;

    @Expose
    private LocalDate dateCreated;

    @Expose
    private Status status;

    @Expose
    private String gateway;

    public PeripheralDeviceAddDto() {
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }
}
