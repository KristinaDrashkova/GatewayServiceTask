package com.musala.gateway.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class PeripheralDeviceUpdateDto {
    @NotNull
    @Expose
    private long id;

    @NotNull
    @Expose
    private PeripheralDeviceAddDto peripheralDeviceAddDto;

    public PeripheralDeviceUpdateDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PeripheralDeviceAddDto getPeripheralDeviceAddDto() {
        return peripheralDeviceAddDto;
    }

    public void setPeripheralDeviceAddDto(PeripheralDeviceAddDto peripheralDeviceAddDto) {
        this.peripheralDeviceAddDto = peripheralDeviceAddDto;
    }
}
