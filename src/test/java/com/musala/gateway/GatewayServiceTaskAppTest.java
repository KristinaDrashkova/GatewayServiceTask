package com.musala.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.gateway.dto.GatewayAddDto;
import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.entities.PeripheralDevice;
import com.musala.gateway.entities.Status;
import org.json.JSONException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GatewayServiceTaskApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class GatewayServiceTaskAppTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity<GatewayAddDto> gatewayRequest;
    private HttpEntity<PeripheralDeviceAddDto> peripheralDeviceRequest;
    private HttpEntity<String> entity;
    private GatewayAddDto gatewayAddDto =
            new GatewayAddDto("1111-2222-3333-4444-5555", "testOne", "255.168.3.24");
    private PeripheralDeviceAddDto peripheralDeviceAddDto =
            new PeripheralDeviceAddDto(1, "IBM", new Date(), Status.OFFLINE, 1);
    private ObjectMapper mapper = new ObjectMapper();
    @Before
    public void setUp() {
        headers.add("Content-Type", "application/json");
        entity = new HttpEntity<>(null, headers);
        gatewayRequest = new HttpEntity<>(gatewayAddDto);
        peripheralDeviceRequest = new HttpEntity<>(peripheralDeviceAddDto);
    }

    @Test
    public void gatewayGetAllShouldWorkCorrectly() throws JSONException, IOException {
        String uri = createURLWithPort("/gateway");
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        Gateway[] gatewaysFromJson = mapper.readValue(response.getBody(), Gateway[].class);
        Assert.assertTrue(gatewaysFromJson[0].getSerialNumber().equals("1111-2222-3333-4444-5555"));
        Assert.assertTrue(gatewaysFromJson[0].getName().equals("testOne"));
        Assert.assertTrue(gatewaysFromJson[0].getIpv4Address().equals("255.168.3.24"));
        Assert.assertTrue(gatewaysFromJson[1].getSerialNumber().equals("0000-2222-3333-4444-5555"));
        Assert.assertTrue(gatewaysFromJson[1].getName().equals("testTwo"));
        Assert.assertTrue(gatewaysFromJson[1].getIpv4Address().equals("255.168.3.34"));
    }

    @Test
    public void gatewayGetOneShouldWorkCorrectly() throws JSONException {
        String uri = createURLWithPort("/gateway/1");
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        Assert.assertTrue(response.getBody().contains("1111-2222-3333-4444-5555"));
        Assert.assertTrue(response.getBody().contains("testOne"));
        Assert.assertTrue(response.getBody().contains("255.168.3.24"));
    }

    @Test
    public void gatewayGetSingleGatewayShouldReturnBadRequestStatusWithInvalidId() {
        String uri = createURLWithPort("/gateway/");
        ResponseEntity<String> response = restTemplate.exchange(uri + "101", HttpMethod.GET, entity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    public void gatewayPostMethodShouldWorkCorrectly() {
        String uri = createURLWithPort("/gateway/");
        ResponseEntity<GatewayAddDto> response = restTemplate
                .exchange(uri + "1", HttpMethod.GET, gatewayRequest, GatewayAddDto.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void gatewayPostMethodShouldReturnBadRequestStatusWithDuplicateSerialNumber() {
        String uri = createURLWithPort("/gateway");
        gatewayRequest = new HttpEntity<>(gatewayAddDto);
        ResponseEntity<String> response = restTemplate.postForEntity(uri, gatewayRequest, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void gatewayPutMethodShouldWorkCorrectly() {
        String uri = createURLWithPort("/gateway/");
        GatewayAddDto gatewayAddDtoTwo =
                new GatewayAddDto("1330-1691-2320-1630-3127-2515", "A", "192.168.3.24");
        gatewayRequest = new HttpEntity<>(gatewayAddDtoTwo);

        restTemplate.put(uri + "1", gatewayRequest, String.class);
        ResponseEntity<GatewayAddDto> response = restTemplate.exchange(
                uri + "1",
                HttpMethod.GET, entity, GatewayAddDto.class);
        GatewayAddDto currentDto = response.getBody();
        assertThat(currentDto, notNullValue());
        assertThat(currentDto.getSerialNumber(), is("1330-1691-2320-1630-3127-2515"));
        assertThat(currentDto.getName(), is("A"));
        assertThat(currentDto.getIpv4Address(), is("192.168.3.24"));
    }

    @Test
    public void gatewayPutMethodShouldReturnBadRequestStatusWithInvalidId() throws Exception {
        String uri = createURLWithPort("/gateway/");
        ResponseEntity<GatewayAddDto> response = restTemplate.exchange(uri + "2",
                HttpMethod.PUT, entity, GatewayAddDto.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    public void peripheralDevicePostMethodShouldWorkCorrectly() {
        String uri = createURLWithPort("/peripheralDevice/");
        restTemplate.postForEntity(uri, peripheralDeviceRequest, String.class);
        PeripheralDevice peripheralDevice = getPeripheralDevice();
        assertNotNull(peripheralDevice);
        assertThat(peripheralDevice.getUid(), is(1));
        assertThat(peripheralDevice.getVendor(), is("IBM"));
        assertThat(peripheralDevice.getStatus(), is(Status.OFFLINE));
    }

    @Test
    public void peripheralDevicePostMethodShouldReturnBadRequestStatusWithDuplicateUid() {
        String uri = createURLWithPort("/peripheralDevice/");
        restTemplate.postForEntity(uri, peripheralDeviceRequest, String.class);
        ResponseEntity<String> response = restTemplate.postForEntity(uri, peripheralDeviceRequest, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void peripheralDevicePutMethodShouldWorkCorrectly() {
        PeripheralDeviceAddDto peripheralDeviceAddDtoTwo =
                new PeripheralDeviceAddDto(1, "INTEL", new Date(), Status.ONLINE, 1);
        String uri = createURLWithPort("/peripheralDevice/");
        restTemplate.postForEntity(uri, peripheralDeviceRequest, String.class);
        peripheralDeviceRequest = new HttpEntity<>(peripheralDeviceAddDtoTwo);
        restTemplate.put(uri + "1", peripheralDeviceRequest);

        PeripheralDevice peripheralDevice = getPeripheralDevice();
        assertNotNull(peripheralDevice);
        assertThat(peripheralDevice.getUid(), is(1));
        assertThat(peripheralDevice.getVendor(), is("INTEL"));
        assertThat(peripheralDevice.getStatus(), is(Status.ONLINE));
    }

    @Test
    public void peripheralDevicePutMethodShouldReturnBadRequestStatusWithInvalidId() {
        String uri = createURLWithPort("/peripheralDevice/101");
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void peripheralDeviceDeleteMethodShouldWorkCorrectly() {
        String uri = createURLWithPort("/peripheralDevice/");
        restTemplate.postForEntity(uri, peripheralDeviceRequest, String.class);
        assertNotNull(getPeripheralDevice());
        restTemplate.delete(uri + "1");
        Assert.assertEquals(null, getPeripheralDevice());
    }

    @Test
    public void peripheralDeviceDeleteMethodShouldReturnBadRequestStatusWithInvalidId() {
        String uri = createURLWithPort("/peripheralDevice/101");
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @After
    public void finalize() {
        restTemplate.delete(createURLWithPort("/gateway/1"));
        restTemplate.delete(createURLWithPort("/peripheralDevice/1"));
    }

    private PeripheralDevice getPeripheralDevice() {
        String uri = createURLWithPort("/gateway/");
        ResponseEntity<Gateway> response = restTemplate
                .exchange(uri + "1", HttpMethod.GET, gatewayRequest, Gateway.class);
        Gateway gateway = response.getBody();
        Iterator<PeripheralDevice> it = gateway.getPeripheralDevices().iterator();

        if (!it.hasNext()) {
            return null;
        }
        return it.next();
    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
