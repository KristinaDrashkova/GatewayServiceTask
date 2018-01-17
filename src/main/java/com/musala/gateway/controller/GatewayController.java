package com.musala.gateway.controller;

import com.musala.gateway.dto.GatewayAddDto;
import com.musala.gateway.dto.GatewayUpdateDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GatewayController {
    @Autowired
    private GatewayService gatewayService;

    @RequestMapping(value = "/updateGateway", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void updateGateway(@RequestBody GatewayUpdateDto gatewayUpdateDto) throws ClassNotFoundException {
        long id = gatewayUpdateDto.getId();
        GatewayAddDto gatewayAddDto = gatewayUpdateDto.getGatewayAddDto();
        gatewayService.updateGateway(id, gatewayAddDto);
    }

    @RequestMapping(value = "/saveGateway", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveGateway(@RequestBody GatewayAddDto gatewayAddDto) {
        gatewayService.save(gatewayAddDto);
    }

    @RequestMapping("/gateways")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public List<Gateway> getInfoAboutAllGateways() {
        List<Gateway> gateways = gatewayService.getAllGateways();

        return gateways;
    }

    @RequestMapping("/gateway")
    @ResponseBody
    public Gateway getInfoAboutAGateway(@RequestParam(value = "id") long id) throws ClassNotFoundException {
        Gateway gateway = gatewayService.getGateway(id);

        if (gateway == null) {
            //error for invalid data
        }

        return gateway;
    }
}
