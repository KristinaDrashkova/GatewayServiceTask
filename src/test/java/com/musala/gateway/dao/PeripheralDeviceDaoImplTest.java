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
public class PeripheralDeviceDaoImplTest {
    @Autowired
    private PeripheralDeviceDao peripheralDeviceDao;

    @PersistenceContext
    private EntityManager em;

    private PeripheralDevice peripheralDevice = new PeripheralDevice();

    @Before
    public void initialize() throws ParseException {
        Gateway gateway = new Gateway();
        gateway.setName("name");
        gateway.setSerialNumber("1245-1234-1234-1235");
        gateway.setIpv4Address("192.168.3.24");
        peripheralDevice = new PeripheralDevice();
        peripheralDevice.setVendor("SteelSeries");
        peripheralDevice.setUid(10);
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        peripheralDevice.setUid(1);
        peripheralDevice.setDateCreated(format.parse("2015-12-20"));
        peripheralDevice.setStatus(Status.OFFLINE);
        Set<PeripheralDevice> peripheralDevices = new LinkedHashSet<>();
        peripheralDevices.add(peripheralDevice);
        gateway.setPeripheralDevices(peripheralDevices);
        peripheralDevice.setGateway(gateway);
        em.persist(gateway);
    }

    @Test
    @Transactional
    @Rollback
    public void findAll() throws Exception {
        em.persist(peripheralDevice);

        List<PeripheralDevice> peripheralDevices = peripheralDeviceDao.findAll();
        Assert.assertEquals(peripheralDevice, peripheralDevices.get(0));
        Assert.assertEquals(1, peripheralDevices.size());
    }

    @Test
    @Transactional
    @Rollback
    public void findByUid() throws Exception {
        em.persist(peripheralDevice);

        PeripheralDevice peripheralDeviceDb = peripheralDeviceDao.findByUid(1);
        Assert.assertEquals(peripheralDevice, peripheralDeviceDb);
    }

    @Test
    @Transactional
    @Rollback
    public void save() throws Exception {
        peripheralDeviceDao.save(peripheralDevice);

        PeripheralDevice peripheralDeviceDb = peripheralDeviceDao.findByUid(1);
        Assert.assertEquals(peripheralDevice, peripheralDeviceDb);
    }

    @Test
    @Transactional
    @Rollback
    public void remove() throws Exception {
        em.persist(peripheralDevice);

        peripheralDeviceDao.remove(1);
        List<PeripheralDevice> peripheralDevices = peripheralDeviceDao.findAll();
        Assert.assertEquals(0, peripheralDevices.size());
    }

}