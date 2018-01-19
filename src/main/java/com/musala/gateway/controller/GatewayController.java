package com.musala.gateway.controller;

import com.musala.gateway.dto.GatewayAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity updateGateway(@PathVariable long id, @RequestBody GatewayAddDto gatewayAddDto) throws ClassNotFoundException {
        gatewayService.updateGateway(id, gatewayAddDto);
        return ResponseEntity.ok().body("Gateway has been updated successfully");
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveGateway(@RequestBody GatewayAddDto gatewayAddDto) {
        gatewayService.save(gatewayAddDto);
        return ResponseEntity.ok().body("Gateway has been saved successfully");
    }

    @RequestMapping(value = {"", "/"})
    @ResponseBody
    public ResponseEntity<List<Gateway>> getInfoAboutAllGateways() {
        List<Gateway> gateways = gatewayService.getAllGateways();
        return ResponseEntity.ok().body(gateways);
    }

    @RequestMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> getInfoAboutAGateway(@PathVariable long id) throws ClassNotFoundException {
        Gateway gateway = gatewayService.getGateway(id);
        return ResponseEntity.ok().body(gateway);
    }

    public void setGatewayService(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }
}
