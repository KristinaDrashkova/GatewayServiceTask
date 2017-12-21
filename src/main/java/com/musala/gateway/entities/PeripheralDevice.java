package com.musala.gateway.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "peripheral_device")
public class PeripheralDevice extends BaseEntity{

    private Integer uid;

    @NotNull
    private String vendor;

    @Column(name = "date_created")
    private Date dateCreated;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "gateway_id", referencedColumnName = "id")
    private Gateway gateway;

    public PeripheralDevice() {
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

    public Gateway getGateway() {
        return gateway;
    }

    public void setGateway(Gateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public String toString() {
        return "PeripheralDevice{" +
                "uid=" + uid +
                ", vendor='" + vendor + '\'' +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                ", gateway=" + gateway +
                '}';
    }
}
