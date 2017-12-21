package com.musala.gateway.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "gateways")
public class Gateway {

    @Id
    @Column(name = "serial_number")
    private String serialNumber;

    @NotNull
    private String name;

    @NotNull
    @Column(name = "ipv4_address")
    private String ipv4Address;

    @NotNull
    @OneToMany(mappedBy = "gateway", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PeripheralDevice> peripheralDevices;

    public Gateway() {
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

    public Set<PeripheralDevice> getPeripheralDevices() {
        return peripheralDevices;
    }

    public void setPeripheralDevices(Set<PeripheralDevice> peripheralDevices) {
        this.peripheralDevices = peripheralDevices;
    }

    @Override
    public String toString() {
        return "Gateway{" +
                "serialNumber='" + serialNumber + '\'' +
                ", name='" + name + '\'' +
                ", ipv4Address='" + ipv4Address + '\'' +
                ", peripheralDevices=" + peripheralDevices.toString() +
                '}';
    }
}
