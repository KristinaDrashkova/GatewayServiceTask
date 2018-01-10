package com.musala.gateway.dto;

import com.google.gson.annotations.Expose;
import com.musala.gateway.entities.Status;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class PeripheralDeviceAddDto {
    @NotNull
    @Expose
    @Column(unique = true)
    private Integer uid;

    @NotNull
    @Expose
    private String vendor;

    @NotNull
    @Expose
    private Date dateCreated;

    @NotNull
    @Expose
    private Status status;

    @NotNull
    @Expose
    private Integer gateway;

    public PeripheralDeviceAddDto() {
    }

    public PeripheralDeviceAddDto(Integer uid, String vendor, Date dateCreated, Status status, Integer gateway) {
        this.uid = uid;
        this.vendor = vendor;
        this.dateCreated = dateCreated;
        this.status = status;
        this.gateway = gateway;
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
