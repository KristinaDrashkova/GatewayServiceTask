package com.musala.gateway.controller;

import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.exceptions.MoreThanTenDevicesException;
import com.musala.gateway.service.PeripheralDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/peripheralDevice")
@RestController
public class PeripheralDeviceController {
    @Autowired
    private PeripheralDeviceService peripheralDeviceService;

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePeripheralDevice(@PathVariable long id, @RequestBody PeripheralDeviceAddDto peripheralDeviceAddDto)
            throws ClassNotFoundException, MoreThanTenDevicesException {
        peripheralDeviceService.updatePeripheralDevice(id, peripheralDeviceAddDto);
        return ResponseEntity.ok().body("Peripheral device has been updated successfully");
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> savePeripheralDevice(@RequestBody PeripheralDeviceAddDto peripheralDeviceAddDto) throws MoreThanTenDevicesException {
        peripheralDeviceService.save(peripheralDeviceAddDto);
        return ResponseEntity.ok().body("Peripheral device has been saved successfully");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removePeripheralDevice(@PathVariable long id) {
        peripheralDeviceService.removeDevice(id);
        return ResponseEntity.ok().body("Peripheral device has been deleted successfully");
    }

    public void setPeripheralDeviceService(PeripheralDeviceService peripheralDeviceService) {
        this.peripheralDeviceService = peripheralDeviceService;
    }
}
