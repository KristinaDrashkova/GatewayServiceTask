package com.musala.gateway.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "peripheral_devices")
public class PeripheralDevice {

    @Id
    @NotNull
    private Integer uid;

    @NotNull
    private String vendor;

    @NotNull
    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @NotNull
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "serial_number", unique = true)
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

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
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
}
