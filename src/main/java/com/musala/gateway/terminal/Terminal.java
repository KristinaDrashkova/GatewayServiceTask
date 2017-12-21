package com.musala.gateway.terminal;

import com.musala.gateway.dto.GatewayAddDto;
import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.service.GatewayService;
import com.musala.gateway.service.PeripheralDeviceService;
import com.musala.gateway.utils.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Terminal implements CommandLineRunner {
    private static final String IMPORT_GATEWAY_PATH = "/json/in/gateways.json";
    private static final String IMPORT_PERIPHERAL_DEVICE_PATH = "/json/in/peripheral_devices.json";
    @Autowired
    private JsonParser jsonParser;
    @Autowired
    private GatewayService gatewayService;
    @Autowired
    private PeripheralDeviceService peripheralDeviceService;


    @Override
    public void run(String... strings) throws Exception {
        importGatewaysFromJson();
        importPeripheralDevicesFromJson();
    }

    private void importGatewaysFromJson() {
        GatewayAddDto[] gatewayAddDtos = jsonParser.getObject(GatewayAddDto[].class, IMPORT_GATEWAY_PATH);
        for (GatewayAddDto gatewayAddDto : gatewayAddDtos) {
            gatewayService.save(gatewayAddDto);
        }
    }

    private void importPeripheralDevicesFromJson() {
        PeripheralDeviceAddDto[] peripheralDeviceAddDtos = jsonParser.getObject(PeripheralDeviceAddDto[].class, IMPORT_PERIPHERAL_DEVICE_PATH);
        for (PeripheralDeviceAddDto peripheralDeviceAddDto : peripheralDeviceAddDtos) {
            peripheralDeviceService.save(peripheralDeviceAddDto);
        }
    }

}
