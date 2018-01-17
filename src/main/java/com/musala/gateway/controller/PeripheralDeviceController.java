package com.musala.gateway.controller;

import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.dto.PeripheralDeviceUpdateDto;
import com.musala.gateway.exceptions.MoreThanTenDevicesException;
import com.musala.gateway.service.PeripheralDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class PeripheralDeviceController {
    @Autowired
    private PeripheralDeviceService peripheralDeviceService;

    @RequestMapping(value = "/updatePeripheralDevice", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updatePeripheralDevice(@RequestBody PeripheralDeviceUpdateDto peripheralDeviceUpdateDto)
            throws ClassNotFoundException, MoreThanTenDevicesException {
        long id = peripheralDeviceUpdateDto.getId();
        PeripheralDeviceAddDto peripheralDeviceAddDto = peripheralDeviceUpdateDto.getPeripheralDeviceAddDto();
        peripheralDeviceService.updatePeripheralDevice(id, peripheralDeviceAddDto);
    }

    @RequestMapping(value = "/savePeripheralDevice", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void savePeripheralDevice(@RequestBody PeripheralDeviceAddDto peripheralDeviceAddDto) throws MoreThanTenDevicesException {
        peripheralDeviceService.save(peripheralDeviceAddDto);
    }

    @RequestMapping(value = "/removePeripheralDeviceId")
    public void removePeripheralDevice(@RequestParam(value = "id") long id) {
        peripheralDeviceService.removeDevice(id);
    }

    @RequestMapping(value = "/removePeripheralDevice", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void removePeripheralDevice(@RequestBody PeripheralDeviceAddDto peripheralDeviceAddDto) {
        peripheralDeviceService.removeDevice(peripheralDeviceAddDto);
    }
}
