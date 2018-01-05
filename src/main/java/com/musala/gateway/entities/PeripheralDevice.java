package com.musala.gateway.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "peripheral_device")
public class PeripheralDevice extends BaseEntity {

    @NotNull
    private Integer uid;

    @NotNull
    private String vendor;

    @NotNull
    @Column(name = "date_created")
    private Date dateCreated;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gateway_id", referencedColumnName = "id")
    private Gateway gateway;

    public PeripheralDevice() {
    }

    public PeripheralDevice(@NotNull Integer uid, @NotNull String vendor, @NotNull Date dateCreated, @NotNull Status status) {
        this.uid = uid;
        this.vendor = vendor;
        this.dateCreated = dateCreated;
        this.status = status;
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
        return "PeripheralDevice { " + System.lineSeparator() +
                "uid = " + uid + System.lineSeparator() +
                "vendor = " + vendor + System.lineSeparator() +
                "dateCreated = " + dateCreated + System.lineSeparator() +
                "status = " + status + System.lineSeparator() +
                "gateway = " + gateway.getName() + System.lineSeparator() +
                '}';
    }
}
