package com.musala.gateway.dto;

import com.google.gson.annotations.Expose;
import com.musala.gateway.entities.Status;

import java.util.Date;

public class PeripheralDeviceAddDto {
    @Expose
    private Integer uid;

    @Expose
    private String vendor;

    @Expose
    private Date dateCreated;

    @Expose
    private Status status;

    @Expose
    private Integer gateway;

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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getGateway() {
        return gateway;
    }

    public void setGateway(Integer gateway) {
        this.gateway = gateway;
    }
}
