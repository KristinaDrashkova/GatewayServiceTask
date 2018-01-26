package com.musala.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.gateway.dto.GatewayAddDto;
import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.entities.Status;
import org.json.JSONException;
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
            new GatewayAddDto("5555-4444-3333-2222-1111", "test", "255.168.3.24");
    private PeripheralDeviceAddDto peripheralDeviceAddDto =
            new PeripheralDeviceAddDto(3, "Test", new Date(), Status.OFFLINE, 2);
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
        String uri = createURLWithPort("/gateway/101");
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    public void gatewayPostMethodShouldWorkCorrectly() {
        String uri = createURLWithPort("/gateway/");
        ResponseEntity<String> response = restTemplate
                .exchange(uri, HttpMethod.POST, gatewayRequest, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertTrue(response.getBody().contains("5555-4444-3333-2222-1111"));
        Assert.assertTrue(response.getBody().contains("test"));
        Assert.assertTrue(response.getBody().contains("255.168.3.24"));
    }

    @Test
    public void gatewayPostMethodShouldReturnBadRequestStatusWithDuplicateSerialNumber() {
        String uri = createURLWithPort("/gateway");
        ResponseEntity<String> response = restTemplate.postForEntity(uri, gatewayRequest, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void gatewayPutMethodShouldWorkCorrectly() {
        String uri = createURLWithPort("/gateway/3");
        gatewayRequest = new HttpEntity<>
                (new GatewayAddDto("1330-1691-2320-1630-3127-2515", "A", "192.168.3.24"));
        restTemplate.put(uri, gatewayRequest, String.class);
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertTrue(response.getBody().contains("1330-1691-2320-1630-3127-2515"));
        Assert.assertTrue(response.getBody().contains("A"));
        Assert.assertTrue(response.getBody().contains("192.168.3.24"));
    }

    @Test
    public void gatewayPutMethodShouldReturnBadRequestStatusWithInvalidId() throws Exception {
        String uri = createURLWithPort("/gateway/101");
        ResponseEntity<GatewayAddDto> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, GatewayAddDto.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    public void peripheralDevicePostMethodShouldWorkCorrectly() {
        String uri = createURLWithPort("/peripheralDevice/");
        peripheralDeviceRequest = new HttpEntity<>(peripheralDeviceAddDto);
        restTemplate.postForEntity(uri, peripheralDeviceRequest, String.class);


    }

    @Test
    public void peripheralDevicePostMethodShouldReturnBadRequestStatusWithDuplicateUid() {
        String uri = createURLWithPort("/peripheralDevice/");
        peripheralDeviceRequest =
                new HttpEntity<>(new PeripheralDeviceAddDto(1, "SEGA", new Date(), Status.ONLINE, 2));
        ResponseEntity<String> response = restTemplate.postForEntity(uri, peripheralDeviceRequest, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void peripheralDevicePutMethodShouldWorkCorrectly() {
        String uri = createURLWithPort("/peripheralDevice/1");
        peripheralDeviceRequest = new HttpEntity<>
                (new PeripheralDeviceAddDto(1, "SEGA", new Date(), Status.ONLINE, 1));
        ResponseEntity<String> response = restTemplate.exchange(uri,HttpMethod.PUT, peripheralDeviceRequest, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void peripheralDevicePutMethodShouldReturnBadRequestStatusWithInvalidId() {
        String uri = createURLWithPort("/peripheralDevice/101");
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void peripheralDeviceDeleteMethodShouldWorkCorrectly() {
        String uri = createURLWithPort("/peripheralDevice/2");
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void peripheralDeviceDeleteMethodShouldReturnBadRequestStatusWithInvalidId() {
        String uri = createURLWithPort("/peripheralDevice/101");
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
