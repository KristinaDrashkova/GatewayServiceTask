package com.musala.gateway.dao;

import com.musala.gateway.JpaConfig;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.entities.PeripheralDevice;
import com.musala.gateway.entities.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {JpaConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class GatewayDaoImplTest {

    @Autowired
    private GatewayDao gatewayDao;

    @PersistenceContext
    private EntityManager em;

    private Gateway gateway = new Gateway();

    @Before
    public void initialize() throws ParseException {
        gateway.setName("name");
        gateway.setSerialNumber("1245-1234-1234-1235");
        gateway.setIpv4Address("192.168.3.24");
        PeripheralDevice peripheralDevice = new PeripheralDevice();
        peripheralDevice.setVendor("SteelSeries");
        peripheralDevice.setUid(10);
        peripheralDevice.setUid(1);
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        peripheralDevice.setDateCreated(format.parse("2015-12-20"));
        peripheralDevice.setStatus(Status.OFFLINE);
        Set<PeripheralDevice> peripheralDevices = new LinkedHashSet<>();
        peripheralDevices.add(peripheralDevice);
        peripheralDevice.setGateway(gateway);
        gateway.setPeripheralDevices(peripheralDevices);
    }

    @Test
    @Transactional
    @Rollback
    public void findAll() {
        em.persist(gateway);

        List<Gateway> gateways = gatewayDao.findAll();
        Assert.assertEquals(gateway.getId(), gateways.get(0).getId());
        Assert.assertEquals(1, gateways.size());
    }

    @Test
    @Transactional
    @Rollback
    public void findById() {
        em.persist(gateway);

        Gateway dbGateway = gatewayDao.findById(1);
        Assert.assertEquals(gateway, dbGateway);
    }

    @Test
    @Transactional
    @Rollback
    public void save() {
        gatewayDao.save(gateway);

        Gateway gatewayDb = gatewayDao.findById(1);
        Assert.assertEquals(gateway, gatewayDb);
    }
}