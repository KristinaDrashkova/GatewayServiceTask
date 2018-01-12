package com.musala.gateway.terminal;

import com.musala.gateway.dto.GatewayAddDto;
import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.exceptions.MoreThanTenDevicesException;
import com.musala.gateway.service.GatewayService;
import com.musala.gateway.service.PeripheralDeviceService;
import com.musala.gateway.utils.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Terminal implements CommandLineRunner {
    private static final String IMPORT_GATEWAY_PATH = "/json/in/gateways.json";
    private static final String IMPORT_PERIPHERAL_DEVICE_PATH = "/json/in/peripheral_devices.json";
    private final JsonParser jsonParser;
    private final GatewayService gatewayService;
    private final PeripheralDeviceService peripheralDeviceService;

    @Autowired
    public Terminal(JsonParser jsonParser, GatewayService gatewayService, PeripheralDeviceService peripheralDeviceService) {
        this.jsonParser = jsonParser;
        this.gatewayService = gatewayService;
        this.peripheralDeviceService = peripheralDeviceService;
    }


    @Override
    public void run(String... strings) throws Exception {
//        importGatewaysFromJson();
//        importPeripheralDevicesFromJson();
//        printInfoAboutAllGateways();
//        printInfoAboutAGateway(2);
//        updateGateway();
    }

    private void updateGateway() throws ClassNotFoundException {
        GatewayAddDto gatewayAddDto = new GatewayAddDto("1330-1691-2320-1630-3127-2516", "B", "192.168.3.24");
        gatewayService.updateGateway(1, gatewayAddDto);
    }

    private void importGatewaysFromJson() {
        GatewayAddDto[] gatewayAddDtos = jsonParser.getObject(GatewayAddDto[].class, IMPORT_GATEWAY_PATH);
        for (GatewayAddDto gatewayAddDto : gatewayAddDtos) {
            gatewayService.save(gatewayAddDto);
        }
    }

    private void importPeripheralDevicesFromJson() throws ClassNotFoundException, MoreThanTenDevicesException {
        PeripheralDeviceAddDto[] peripheralDeviceAddDtos = jsonParser.getObject(PeripheralDeviceAddDto[].class, IMPORT_PERIPHERAL_DEVICE_PATH);
        for (PeripheralDeviceAddDto peripheralDeviceAddDto : peripheralDeviceAddDtos) {
            peripheralDeviceService.save(peripheralDeviceAddDto);
        }
    }

    private void printInfoAboutAllGateways() {
        List<Gateway> gateways = gatewayService.getAllGateways();
        for (Gateway gateway : gateways) {
            System.out.println(gateway.toString());
        }
    }

    private void printInfoAboutAGateway(long id) throws ClassNotFoundException {
        Gateway gateway = gatewayService.getGateway(id);
        System.out.println(gateway);
    }
}
