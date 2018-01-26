package com.musala.gateway.dao;

import com.musala.gateway.entities.PeripheralDevice;
import com.musala.gateway.entities.Status;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PeripheralDeviceDaoImplTest {
    @Autowired
    private PeripheralDeviceDao peripheralDeviceDao;
    private PeripheralDevice peripheralDeviceRepeatingUid =
            new PeripheralDevice(3, "IBM", new Date(), Status.ONLINE);
    private PeripheralDevice peripheralDeviceNormal =
            new PeripheralDevice(3, "SteelSeries", new Date(), Status.OFFLINE);
    private PeripheralDevice peripheralDeviceNullUid =
            new PeripheralDevice(null, "SteelSeries", new Date(), Status.OFFLINE);
    private PeripheralDevice peripheralDeviceNullVendor =
            new PeripheralDevice(3, null, new Date(), Status.OFFLINE);
    private PeripheralDevice peripheralDeviceNullDate =
            new PeripheralDevice(null, "SteelSeries", null, Status.OFFLINE);
    private PeripheralDevice peripheralDeviceNullStatus =
            new PeripheralDevice(3, "SteelSeries", new Date(), null);

    @PersistenceContext
    private EntityManager em;


    @Transactional
    @SuppressWarnings("unchecked")
    @Test
    public void findAllShouldWorkCorrectly() {
        List<PeripheralDevice> peripheralDevices = peripheralDeviceDao.findAll();
        Assert.assertEquals(2, peripheralDevices.size());
        em.persist(peripheralDeviceNormal);

        peripheralDevices = peripheralDeviceDao.findAll();
        Assert.assertTrue(peripheralDevices.contains(peripheralDeviceNormal));
        Assert.assertEquals(3, peripheralDevices.size());
    }

    @Transactional
    @Test
    public void findByIdShouldWorkCorrectly() throws ClassNotFoundException {
        em.persist(peripheralDeviceNormal);

        PeripheralDevice peripheralDeviceDb = peripheralDeviceDao.findById(peripheralDeviceNormal.getId());
        PeripheralDevice peripheralDeviceNull = peripheralDeviceDao.findById(10);
        Assert.assertEquals(peripheralDeviceNormal, peripheralDeviceDb);
        Assert.assertEquals(null, peripheralDeviceNull);
    }

    @Transactional
    @Test
    public void saveShouldWorkCorrectly() throws Exception {
        peripheralDeviceDao.save(peripheralDeviceNormal);

        PeripheralDevice peripheralDevice = peripheralDeviceDao.findById(peripheralDeviceNormal.getId());
        Assert.assertEquals(peripheralDeviceNormal, peripheralDevice);
    }

    @Transactional
    @Test(expected = DataIntegrityViolationException.class)
    public void saveShouldThrowExceptionWithRepeatingUid() {
        peripheralDeviceDao.save(peripheralDeviceNormal);
        peripheralDeviceDao.save(peripheralDeviceRepeatingUid);
    }

    @Transactional
    @Test(expected = ConstraintViolationException.class)
    public void saveShouldThrowExceptionWithNullUid() throws Exception {
        peripheralDeviceDao.save(peripheralDeviceNullUid);
    }

    @Transactional
    @Test(expected = ConstraintViolationException.class)
    public void saveShouldThrowExceptionWithNullVendor() throws Exception {
        peripheralDeviceDao.save(peripheralDeviceNullVendor);
    }

    @Transactional
    @Test(expected = ConstraintViolationException.class)
    public void saveShouldThrowExceptionWithNullStatus() throws Exception {
        peripheralDeviceDao.save(peripheralDeviceNullStatus);
    }

    @Transactional
    @Test(expected = ConstraintViolationException.class)
    public void saveShouldThrowExceptionWithNullDate() throws Exception {
        peripheralDeviceDao.save(peripheralDeviceNullDate);
    }

    @Transactional
    @Test
    public void removeShouldWorkCorrectly() throws Exception {
        em.persist(peripheralDeviceNormal);
        List<PeripheralDevice> peripheralDevices = peripheralDeviceDao.findAll();
        Assert.assertTrue(peripheralDevices.contains(peripheralDeviceNormal));
        peripheralDeviceDao.remove(peripheralDeviceNormal);
        peripheralDevices = peripheralDeviceDao.findAll();
        Assert.assertEquals(2, peripheralDevices.size());
    }
}