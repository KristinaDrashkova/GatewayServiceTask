package com.musala.gateway.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "peripheral_devices")
public class PeripheralDevice {

    @Id
    private Integer uid;

    @NotNull
    private String vendor;

    @NotNull
    @Column(name = "date_created")
    private LocalDate dateCreated;

    @NotNull
    private Status status;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "gateway_serial_number", referencedColumnName = "serial_number")
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
