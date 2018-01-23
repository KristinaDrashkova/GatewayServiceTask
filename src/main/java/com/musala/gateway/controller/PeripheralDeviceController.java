package com.musala.gateway.controller;

import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.exceptions.ModelNotFoundException;
import com.musala.gateway.exceptions.MoreThanTenDevicesException;
import com.musala.gateway.service.PeripheralDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            throws ClassNotFoundException, MoreThanTenDevicesException, ModelNotFoundException {
        peripheralDeviceService.updatePeripheralDevice(id, peripheralDeviceAddDto);
        return new ResponseEntity<>("Peripheral device has been updated successfully", HttpStatus.OK);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> savePeripheralDevice(@RequestBody PeripheralDeviceAddDto peripheralDeviceAddDto) throws MoreThanTenDevicesException, ModelNotFoundException {
        peripheralDeviceService.savePeripheralDevice(peripheralDeviceAddDto);
        return new ResponseEntity<>("Peripheral device has been saved successfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removePeripheralDevice(@PathVariable long id) throws ModelNotFoundException {
        peripheralDeviceService.removeDevice(id);
        return new ResponseEntity<>("Peripheral device has been deleted successfully", HttpStatus.OK);
    }

    public void setPeripheralDeviceService(PeripheralDeviceService peripheralDeviceService) {
        this.peripheralDeviceService = peripheralDeviceService;
    }
}
