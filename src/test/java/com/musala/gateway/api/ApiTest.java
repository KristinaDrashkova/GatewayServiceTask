package com.musala.gateway.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.gateway.controller.GatewayController;
import com.musala.gateway.controller.PeripheralDeviceController;
import com.musala.gateway.dto.GatewayAddDto;
import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.entities.PeripheralDevice;
import com.musala.gateway.entities.Status;
import com.musala.gateway.exceptions.ModelNotFoundException;
import com.musala.gateway.service.GatewayService;
import com.musala.gateway.service.PeripheralDeviceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ApiTest {
    private MockMvc mockMvcGateway;
    private MockMvc mockMvcPeripheralDevice;
    private Gateway gatewayOne = new Gateway("1245-1234-1234-1235", "one", "192.168.3.24");
    private Gateway gatewayTwo = new Gateway("1234-1234-1234-1235", "two", "192.168.3.24");
    private PeripheralDevice peripheralDevice = new PeripheralDevice(1, "IBM", new Date(), Status.ONLINE);
    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    @Mock
    private GatewayService gatewayService;

    @Mock
    private PeripheralDeviceService peripheralDeviceService;

    @Before
    public void setUp() throws ModelNotFoundException, ClassNotFoundException {
        GatewayController gatewayController = new GatewayController();
        PeripheralDeviceController peripheralDeviceController = new PeripheralDeviceController();
        gatewayController.setGatewayService(gatewayService);
        peripheralDeviceController.setPeripheralDeviceService(peripheralDeviceService);
        mockMvcGateway = MockMvcBuilders.standaloneSetup(gatewayController).build();
        mockMvcPeripheralDevice = MockMvcBuilders.standaloneSetup(peripheralDeviceController).build();
        gatewayOne.setId(1L);
        gatewayTwo.setId(2L);
        Mockito.when(gatewayService.getAllGateways()).thenReturn(Arrays.asList(gatewayOne, gatewayTwo));
        Mockito.when(gatewayService.getGateway(1)).thenReturn(gatewayOne);
        Mockito.when(gatewayService.getGateway(2)).thenThrow(new ModelNotFoundException(2, Gateway.class.getSimpleName()));
    }

    @Test
    public void gatewayGetAllShouldWorkCorrectly() throws Exception {
        mockMvcGateway.perform(get("/gateway").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].serialNumber", is("1245-1234-1234-1235")))
                .andExpect(jsonPath("$[0].name", is("one")))
                .andExpect(jsonPath("$[0].ipv4Address", is("192.168.3.24")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].serialNumber", is("1234-1234-1234-1235")))
                .andExpect(jsonPath("$[1].name", is("two")))
                .andExpect(jsonPath("$[1].ipv4Address", is("192.168.3.24")));

        verify(gatewayService, times(1)).getAllGateways();
        verifyNoMoreInteractions(gatewayService);
    }

    @Test
    public void gatewayGetSingleGatewayShouldWorkCorrectly() throws Exception {
        mockMvcGateway.perform(get("/gateway/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.serialNumber", is("1245-1234-1234-1235")))
                .andExpect(jsonPath("$.name", is("one")))
                .andExpect(jsonPath("$.ipv4Address", is("192.168.3.24")));

        verify(gatewayService, times(1)).getGateway(1);
        verifyNoMoreInteractions(gatewayService);
    }

    @Test
    public void gatewayGetSingleGatewayShouldReturnBadRequestStatusWithInvalidId() throws Exception {
        mockMvcGateway.perform(get("/gateway/2").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
        verify(gatewayService, times(1)).getGateway(2);
        verifyNoMoreInteractions(gatewayService);
    }

    @Test
    public void gatewayPostMethodShouldWorkCorrectly() throws Exception {
        String json = mapper.writeValueAsString(gatewayTwo);
        GatewayAddDto gatewayAddDto = mapper.readValue(json, GatewayAddDto.class);
        mockMvcGateway.perform(post("/gateway").contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
                .andExpect(status().isOk());
        verify(gatewayService, times(1)).saveGateway(gatewayAddDto);
        verifyNoMoreInteractions(gatewayService);
    }

//    @Test
//    public void gatewayPostMethodShouldReturnBadRequestStatusWithDuplicateId() throws Exception {
//        String json = mapper.writeValueAsString(gatewayOne);
//        GatewayAddDto gatewayAddDto = mapper.readValue(json, GatewayAddDto.class);
//        doThrow(DataIntegrityViolationException.class).when(gatewayService)
//                .saveGateway(gatewayAddDto);
//        mockMvcGateway.perform(post("/gateway/").contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
//                .andExpect(status().isBadRequest());
//    }

    @Test
    public void gatewayPutMethodShouldWorkCorrectly() throws Exception {
        String json = mapper.writeValueAsString(gatewayTwo);
        GatewayAddDto gatewayAddDto = mapper.readValue(json, GatewayAddDto.class);
        mockMvcGateway.perform(put("/gateway/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
                .andExpect(status().isOk());
        verify(gatewayService, times(1)).updateGateway(1, gatewayAddDto);
        verifyNoMoreInteractions(gatewayService);
    }

    @Test
    public void gatewayPutMethodShouldReturnBadRequestStatusWithInvalidId() throws Exception {
        String json = mapper.writeValueAsString(gatewayTwo);
        GatewayAddDto gatewayAddDto = mapper.readValue(json, GatewayAddDto.class);
        doThrow(ModelNotFoundException.class).when(gatewayService)
                .updateGateway(2, gatewayAddDto);
        mockMvcGateway.perform(put("/gateway/2").contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void peripheralDevicePostMethodShouldWorkCorrectly() throws Exception {
        String json = mapper.writeValueAsString(peripheralDevice);
        PeripheralDeviceAddDto peripheralDeviceAddDto = mapper.readValue(json, PeripheralDeviceAddDto.class);
        mockMvcPeripheralDevice
                .perform(post("/peripheralDevice").contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
                .andExpect(status().isOk());
        verify(peripheralDeviceService, times(1)).savePeripheralDevice(peripheralDeviceAddDto);
        verifyNoMoreInteractions(peripheralDeviceService);
    }

//    @Test
//    public void peripheralDevicePostMethodShouldReturnBadRequestStatusWithInvalidId() throws Exception {
//        String json = mapper.writeValueAsString(peripheralDevice);
//        PeripheralDeviceAddDto peripheralDeviceAddDto = mapper.readValue(json, PeripheralDeviceAddDto.class);
//        doThrow(DataIntegrityViolationException.class).when(peripheralDeviceService)
//                .savePeripheralDevice(peripheralDeviceAddDto);
//        mockMvcPeripheralDevice.perform(post("/peripheralDevice/").contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
//                .andExpect(status().isBadRequest());
//    }

    @Test
    public void peripheralDevicePutMethodShouldWorkCorrectly() throws Exception {
        String json = mapper.writeValueAsString(peripheralDevice);
        PeripheralDeviceAddDto peripheralDeviceAddDto = mapper.readValue(json, PeripheralDeviceAddDto.class);
        mockMvcPeripheralDevice
                .perform(put("/peripheralDevice/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
                .andExpect(status().isOk());
        verify(peripheralDeviceService, times(1)).updatePeripheralDevice(1, peripheralDeviceAddDto);
        verifyNoMoreInteractions(peripheralDeviceService);
    }

    @Test
    public void peripheralDevicePutMethodShouldReturnBadRequestStatusWithInvalidId() throws Exception {
        String json = mapper.writeValueAsString(peripheralDevice);
        PeripheralDeviceAddDto peripheralDeviceAddDto = mapper.readValue(json, PeripheralDeviceAddDto.class);
        doThrow(ModelNotFoundException.class).when(peripheralDeviceService)
                .updatePeripheralDevice(1, peripheralDeviceAddDto);
        mockMvcPeripheralDevice
                .perform(put("/peripheralDevice/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void peripheralDeviceDeleteMethodShouldWorkCorrectly() throws Exception {
        String json = mapper.writeValueAsString(peripheralDevice);
        mockMvcPeripheralDevice
                .perform(delete("/peripheralDevice/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
                .andExpect(status().isOk());
        verify(peripheralDeviceService, times(1)).removeDevice(1);
        verifyNoMoreInteractions(peripheralDeviceService);
    }

    @Test
    public void peripheralDeviceDeleteMethodShouldReturnBadRequestStatusWithInvalidId() throws Exception {
        String json = mapper.writeValueAsString(peripheralDevice);
        doThrow(ModelNotFoundException.class).when(peripheralDeviceService).removeDevice(1);
        mockMvcPeripheralDevice
                .perform(delete("/peripheralDevice/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
                .andExpect(status().isBadRequest());
    }
}
