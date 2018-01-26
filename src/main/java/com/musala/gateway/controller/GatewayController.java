package com.musala.gateway.controller;

import com.musala.gateway.dto.GatewayAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.exceptions.ModelNotFoundException;
import com.musala.gateway.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/gateway")
@RestController
public class GatewayController {
    @Autowired
    private GatewayService gatewayService;

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateGateway(@PathVariable long id, @RequestBody GatewayAddDto gatewayAddDto)
            throws ClassNotFoundException, ModelNotFoundException {
        gatewayService.updateGateway(id, gatewayAddDto);
        return new ResponseEntity<>("Gateway has been updated successfully", HttpStatus.OK);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveGateway(@RequestBody GatewayAddDto gatewayAddDto) {
        Gateway gateway = gatewayService.saveGateway(gatewayAddDto);
        return new ResponseEntity<>(gateway, HttpStatus.OK);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<List<Gateway>> getInfoAboutAllGateways() {
        List<Gateway> gateways = gatewayService.getAllGateways();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(gateways, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getInfoAboutAGateway(@PathVariable long id)
            throws ClassNotFoundException, ModelNotFoundException {
        Gateway gateway = gatewayService.getGateway(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(gateway, headers, HttpStatus.OK);
    }

    public void setGatewayService(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }
}
