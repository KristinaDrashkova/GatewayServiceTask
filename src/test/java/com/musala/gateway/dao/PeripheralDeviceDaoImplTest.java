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
    public void setUp() throws ParseException {
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
    public void findAllShouldWorkCorrectly() throws Exception {
        em.persist(peripheralDevice);

        List<PeripheralDevice> peripheralDevices = peripheralDeviceDao.findAll();
        Assert.assertEquals(peripheralDevice, peripheralDevices.get(0));
        Assert.assertEquals(1, peripheralDevices.size());
    }

    @Test
    @Transactional
    @Rollback
    public void findByUidShouldWorkCorrectly() throws Exception {
        em.persist(peripheralDevice);

        PeripheralDevice peripheralDeviceDb = peripheralDeviceDao.findByUid(peripheralDevice.getUid());
        Assert.assertEquals(peripheralDevice, peripheralDeviceDb);
    }

    @Test
    @Transactional
    @Rollback
    public void saveShouldWorkCorrectly() throws Exception {
        peripheralDeviceDao.save(peripheralDevice);

        PeripheralDevice peripheralDeviceDb = peripheralDeviceDao.findByUid(peripheralDevice.getUid());
        PeripheralDevice peripheralDeviceNull = peripheralDeviceDao.findByUid(2);
        Assert.assertEquals(peripheralDevice, peripheralDeviceDb);
        Assert.assertEquals(null, peripheralDeviceNull);
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    @Rollback
    public void saveShouldThrowExceptionWithNull() {
        peripheralDeviceDao.save(null);

    }

    @Test
    @Transactional
    @Rollback
    public void removeShouldWorkCorrectly() throws Exception {
        em.persist(peripheralDevice);

        peripheralDeviceDao.remove(peripheralDevice.getUid());
        List<PeripheralDevice> peripheralDevices = peripheralDeviceDao.findAll();
        Assert.assertEquals(0, peripheralDevices.size());
    }


    @Test(expected = IllegalArgumentException.class)
    @Transactional
    @Rollback
    public void removeShouldThrowExceptionWithNoExistingNumber() throws Exception {
        em.persist(peripheralDevice);

        peripheralDeviceDao.remove(10);
    }
}