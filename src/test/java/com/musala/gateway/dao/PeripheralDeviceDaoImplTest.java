package com.musala.gateway.dao;

import com.musala.gateway.JpaConfig;
import com.musala.gateway.dto.PeripheralDeviceAddDto;
import com.musala.gateway.entities.PeripheralDevice;
import com.musala.gateway.entities.Status;
import com.musala.gateway.utils.ModelParser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {JpaConfig.class},
        loader = AnnotationConfigContextLoader.class)
public class PeripheralDeviceDaoImplTest {
    @Autowired
    private PeripheralDeviceDao peripheralDeviceDao;

    private PeripheralDevice peripheralDeviceRepeatingUid = new PeripheralDevice(1, "IBM", new Date(), Status.ONLINE);
    private PeripheralDevice peripheralDeviceNormal = new PeripheralDevice(1, "SteelSeries", new Date(), Status.OFFLINE);
    private PeripheralDevice peripheralDeviceNullUid = new PeripheralDevice(null, "SteelSeries",  new Date(), Status.OFFLINE);
    private PeripheralDevice peripheralDeviceNullVendor = new PeripheralDevice(1, null,  new Date(), Status.OFFLINE);
    private PeripheralDevice peripheralDeviceNullDate = new PeripheralDevice(null, "SteelSeries", null, Status.OFFLINE);
    private PeripheralDevice peripheralDeviceNullStatus = new PeripheralDevice(1, "SteelSeries",  new Date(), null);
    private PeripheralDeviceAddDto peripheralDeviceAddDto = new PeripheralDeviceAddDto(10, "SteelSeries", new Date(), Status.OFFLINE, 1);

    @PersistenceContext
    private EntityManager em;


    @Transactional
    @SuppressWarnings("unchecked")
    @Test
    public void findAllShouldWorkCorrectly() {
        List<PeripheralDevice> peripheralDevices = peripheralDeviceDao.findAll();
        Assert.assertEquals(0, peripheralDevices.size());
        em.persist(peripheralDeviceNormal);

        peripheralDevices = peripheralDeviceDao.findAll();
        Assert.assertTrue(peripheralDevices.contains(peripheralDeviceNormal));
        Assert.assertEquals(1, peripheralDevices.size());
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
    @Test(expected = PersistenceException.class)
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

        peripheralDeviceDao.remove(peripheralDeviceNormal);
        List<PeripheralDevice> peripheralDevices = peripheralDeviceDao.findAll();
        Assert.assertEquals(0, peripheralDevices.size());
    }

    @Transactional
    @Test(expected = IllegalArgumentException.class)
    public void removeShouldThrowExceptionWithNoExistingNumber() throws Exception {
        em.persist(peripheralDeviceNormal);

        peripheralDeviceDao.remove(10);
    }

    @Transactional
    @Test
    public void updateShouldWorkCorrectly() throws ClassNotFoundException {
        em.persist(peripheralDeviceNormal);
        PeripheralDevice peripheralDeviceFromDto =
                ModelParser.getInstance().map(peripheralDeviceAddDto, PeripheralDevice.class);
        peripheralDeviceDao.update(peripheralDeviceNormal, peripheralDeviceFromDto);
        PeripheralDevice peripheralDevice = peripheralDeviceDao.findAll().get(0);
        Assert.assertEquals(peripheralDevice, peripheralDeviceFromDto);
    }

}