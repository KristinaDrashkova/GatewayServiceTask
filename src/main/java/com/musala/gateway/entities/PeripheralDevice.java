package com.musala.gateway.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

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
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof PeripheralDevice)) {
            return false;
        }

        PeripheralDevice peripheralDevice = (PeripheralDevice) object;

        return Objects.equals(uid, peripheralDevice.uid) &&
                Objects.equals(vendor, peripheralDevice.vendor) &&
                Objects.equals(dateCreated, peripheralDevice.dateCreated) &&
                Objects.equals(status, peripheralDevice.status) &&
                Objects.equals(gateway, peripheralDevice.gateway);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, vendor, dateCreated, status, gateway);
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
