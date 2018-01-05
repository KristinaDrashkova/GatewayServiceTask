package com.musala.gateway.dao;

import com.musala.gateway.JpaConfig;
import com.musala.gateway.entities.BaseEntity;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.entities.PeripheralDevice;
import com.musala.gateway.entities.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {JpaConfig.class},
        loader = AnnotationConfigContextLoader.class)
public class BaseDaoImplTest {
    private BaseEntity GATEWAY_NORMAL = new Gateway("1245-1234-1234-1235", "name", "192.168.3.24");
    private BaseEntity GATEWAY_NULL_SERIAL_NUMBER = new Gateway(null, "name", "192.168.3.24");
    private BaseEntity GATEWAY_NULL_NAME = new Gateway("1245-1234-1234-1235", null, "192.168.3.24");
    private BaseEntity GATEWAY_NULL_ADDRESS = new Gateway("1245-1234-1234-1235", "name", null);
    private BaseEntity PERIPHERAL_DEVICE_NORMAL = new PeripheralDevice(1, "SteelSeries", parseDate(), Status.OFFLINE);
    private BaseEntity PERIPHERAL_DEVICE_NULL_UID = new PeripheralDevice(null, "SteelSeries", parseDate(), Status.OFFLINE);
    private BaseEntity PERIPHERAL_DEVICE_NULL_VENDOR = new PeripheralDevice(1, null, parseDate(), Status.OFFLINE);
    private BaseEntity PERIPHERAL_DEVICE_NULL_DATE = new PeripheralDevice(null, "SteelSeries", null, Status.OFFLINE);
    private BaseEntity PERIPHERAL_DEVICE_NULL_STATUS = new PeripheralDevice(1, "SteelSeries", parseDate(), null);

    @PersistenceContext
    private EntityManager em;

    @Qualifier("baseDaoImpl")
    @Autowired
    private BaseDao baseDao;

    @Before
    public void setUp() {
        Set<PeripheralDevice> peripheralDevices = new LinkedHashSet<>();
        peripheralDevices.add((PeripheralDevice) PERIPHERAL_DEVICE_NORMAL);
        ((Gateway) GATEWAY_NORMAL).setPeripheralDevices(peripheralDevices);
        ((Gateway) GATEWAY_NULL_ADDRESS).setPeripheralDevices(peripheralDevices);
        ((Gateway) GATEWAY_NULL_NAME).setPeripheralDevices(peripheralDevices);
        ((Gateway) GATEWAY_NULL_SERIAL_NUMBER).setPeripheralDevices(peripheralDevices);
    }

    @Test
    public void findAllShouldWorkCorrectly() throws Exception {
        List<BaseEntity> baseEntities = baseDao.findAll();
        Assert.assertEquals(0, baseEntities.size());
        em.persist(GATEWAY_NORMAL);

        baseEntities = baseDao.findAll();
        Assert.assertTrue(baseEntities.contains(GATEWAY_NORMAL));
        Assert.assertTrue(baseEntities.contains(PERIPHERAL_DEVICE_NORMAL));
        Assert.assertEquals(2, baseEntities.size());
    }

    @Test
    public void findByIdShouldWorkCorrectly() throws Exception {
        em.persist(GATEWAY_NORMAL);

        BaseEntity baseEntityDb = baseDao.findById(GATEWAY_NORMAL.getId());
        BaseEntity baseEntityNull = baseDao.findById(10);
        Assert.assertEquals(GATEWAY_NORMAL, baseEntityDb);
        Assert.assertEquals(null, baseEntityNull);
    }

    @Test
    public void saveShouldWorkCorrectlyWithNormalGateway() throws Exception {
        baseDao.save(GATEWAY_NORMAL);

        BaseEntity baseEntityDb = baseDao.findById(GATEWAY_NORMAL.getId());
        Assert.assertEquals(GATEWAY_NORMAL, baseEntityDb);
    }

    @Test
    public void saveShouldWorkCorrectlyWithNormalPeripheralDevice() throws Exception {
        baseDao.save(PERIPHERAL_DEVICE_NORMAL);

        BaseEntity baseEntityDb = baseDao.findById(PERIPHERAL_DEVICE_NORMAL.getId());
        Assert.assertEquals(PERIPHERAL_DEVICE_NORMAL, baseEntityDb);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveShouldThrowExceptionWithNullAddressGateway() throws Exception {
        baseDao.save(GATEWAY_NULL_ADDRESS);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveShouldThrowExceptionWithNullNameGateway() throws Exception {
        baseDao.save(GATEWAY_NULL_NAME);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveShouldThrowExceptionWithNullSerialNumberGateway() throws Exception {
        baseDao.save(GATEWAY_NULL_SERIAL_NUMBER);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveShouldThrowExceptionWithNullUidPeripheralDevice() throws Exception {
        baseDao.save(PERIPHERAL_DEVICE_NULL_UID);
    }
    @Test(expected = ConstraintViolationException.class)
    public void saveShouldThrowExceptionWithNullVendorPeripheralDevice() throws Exception {
        baseDao.save(PERIPHERAL_DEVICE_NULL_VENDOR);
    }
    @Test(expected = ConstraintViolationException.class)
    public void saveShouldThrowExceptionWithNullDatePeripheralDevice() throws Exception {
        baseDao.save(PERIPHERAL_DEVICE_NULL_DATE);
    }
    @Test(expected = ConstraintViolationException.class)
    public void saveShouldThrowExceptionWithNullStatusPeripheralDevice() throws Exception {
        baseDao.save(PERIPHERAL_DEVICE_NULL_STATUS);
    }

    private Date parseDate() {
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse("2015-12-20");
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}