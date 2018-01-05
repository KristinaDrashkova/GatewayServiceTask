package com.musala.gateway.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "gateway")
public class Gateway extends BaseEntity {

    @NotNull
    @Column(name = "serial_number", unique = true)
    private String serialNumber;

    @NotNull
    private String name;

    @NotNull
    @Column(name = "ipv4_address")
    private String ipv4Address;

    @OneToMany(mappedBy = "gateway", cascade = CascadeType.ALL)
    private Set<PeripheralDevice> peripheralDevices;

    public Gateway() {
    }

    public Gateway(@NotNull String serialNumber, @NotNull String name, @NotNull String ipv4Address) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.ipv4Address = ipv4Address;
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
        return "Gateway {" + System.lineSeparator() +
                "serialNumber = " + serialNumber + System.lineSeparator() +
                "name = " + name + System.lineSeparator() +
                "ipv4Address = " + ipv4Address + System.lineSeparator() +
                "peripheralDevices count: " + peripheralDevices.size() +
                '}';
    }
}
