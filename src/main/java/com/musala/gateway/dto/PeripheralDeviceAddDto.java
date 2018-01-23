package com.musala.gateway.dto;

import com.google.gson.annotations.Expose;
import com.musala.gateway.entities.Status;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

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
    private long gateway;

    public PeripheralDeviceAddDto() {
    }

    public PeripheralDeviceAddDto(Integer uid, String vendor, Date dateCreated, Status status, long gateway) {
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

    public long getGateway() {
        return gateway;
    }

    public void setGateway(long gateway) {
        this.gateway = gateway;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof PeripheralDeviceAddDto)) {
            return false;
        }

        PeripheralDeviceAddDto peripheralDeviceAddDto = (PeripheralDeviceAddDto) object;

        return Objects.equals(uid, peripheralDeviceAddDto.uid) &&
                Objects.equals(vendor, peripheralDeviceAddDto.vendor) &&
                Objects.equals(dateCreated, peripheralDeviceAddDto.dateCreated) &&
                Objects.equals(status, peripheralDeviceAddDto.status) &&
                Objects.equals(gateway, peripheralDeviceAddDto.gateway);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, vendor, dateCreated, status, gateway);
    }

}
