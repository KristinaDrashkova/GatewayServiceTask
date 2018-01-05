//package com.musala.gateway.dao;
//
//import com.musala.gateway.JpaConfig;
//import com.musala.gateway.entities.BaseEntity;
//import com.musala.gateway.entities.Gateway;
//import com.musala.gateway.entities.PeripheralDevice;
//import com.musala.gateway.entities.Status;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.transaction.Transactional;
//import javax.validation.ConstraintViolationException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(
//        classes = {JpaConfig.class},
//        loader = AnnotationConfigContextLoader.class)
//public class BaseDaoImplTest {
//    private BaseEntity gatewayNormal = new Gateway("1245-1234-1234-1235", "name", "192.168.3.24");
//    private BaseEntity gatewayNullSerialNumber = new Gateway(null, "name", "192.168.3.24");
//    private BaseEntity gatewayNullName = new Gateway("1245-1234-1234-1235", null, "192.168.3.24");
//    private BaseEntity gatewayNullAddress = new Gateway("1245-1234-1234-1235", "name", null);
//
//    private BaseEntity peripheralDeviceNormal = new PeripheralDevice(1, "SteelSeries", parseDate(), Status.OFFLINE);
//    private BaseEntity peripheralDeviceNullUid = new PeripheralDevice(null, "SteelSeries", parseDate(), Status.OFFLINE);
//    private BaseEntity peripheralDeviceNullVendor = new PeripheralDevice(1, null, parseDate(), Status.OFFLINE);
//    private BaseEntity peripheralDeviceNullDate = new PeripheralDevice(null, "SteelSeries", null, Status.OFFLINE);
//    private BaseEntity peripheralDeviceNullStatus = new PeripheralDevice(1, "SteelSeries", parseDate(), null);
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Qualifier("baseDaoImpl")
//    @Autowired
//    private BaseDao baseDao;
//
//    @Before
//    public void setUp() {
//        Set<PeripheralDevice> peripheralDevices = new LinkedHashSet<>();
//        peripheralDevices.add((PeripheralDevice) peripheralDeviceNormal);
//        ((Gateway) gatewayNormal).setPeripheralDevices(peripheralDevices);
//        ((Gateway) gatewayNullAddress).setPeripheralDevices(peripheralDevices);
//        ((Gateway) gatewayNullName).setPeripheralDevices(peripheralDevices);
//        ((Gateway) gatewayNullSerialNumber).setPeripheralDevices(peripheralDevices);
//    }
//
//    @Transactional
//    @Test
//    public void findAllShouldWorkCorrectly() throws Exception {
//        List<BaseEntity> baseEntities = baseDao.findAll();
//        Assert.assertEquals(0, baseEntities.size());
//        em.persist(gatewayNormal);
//
//        baseEntities = baseDao.findAll();
//        Assert.assertTrue(baseEntities.contains(gatewayNormal));
//        Assert.assertTrue(baseEntities.contains(peripheralDeviceNormal));
//        Assert.assertEquals(2, baseEntities.size());
//    }
//
//    @Transactional
//    @Test
//    public void findByIdShouldWorkCorrectly() throws Exception {
//        em.persist(gatewayNormal);
//
//        BaseEntity baseEntityDb = baseDao.findById(gatewayNormal.getId());
//        BaseEntity baseEntityNull = baseDao.findById(10);
//        Assert.assertEquals(gatewayNormal, baseEntityDb);
//        Assert.assertEquals(null, baseEntityNull);
//    }
//
//    @Transactional
//    @Test
//    public void saveShouldWorkCorrectlyWithNormalGateway() throws Exception {
//        baseDao.save(gatewayNormal);
//
//        BaseEntity baseEntityDb = baseDao.findById(gatewayNormal.getId());
//        Assert.assertEquals(gatewayNormal, baseEntityDb);
//    }
//
//    @Transactional
//    @Test
//    public void saveShouldWorkCorrectlyWithNormalPeripheralDevice() throws Exception {
//        baseDao.save(peripheralDeviceNormal);
//
//        BaseEntity baseEntityDb = baseDao.findById(peripheralDeviceNormal.getId());
//        Assert.assertEquals(peripheralDeviceNormal, baseEntityDb);
//    }
//
//    @Transactional
//    @Test(expected = ConstraintViolationException.class)
//    public void saveShouldThrowExceptionWithNullAddressGateway() throws Exception {
//        baseDao.save(gatewayNullAddress);
//    }
//
//    @Transactional
//    @Test(expected = ConstraintViolationException.class)
//    public void saveShouldThrowExceptionWithNullNameGateway() throws Exception {
//        baseDao.save(gatewayNullName);
//    }
//
//    @Transactional
//    @Test(expected = ConstraintViolationException.class)
//    public void saveShouldThrowExceptionWithNullSerialNumberGateway() throws Exception {
//        baseDao.save(gatewayNullSerialNumber);
//    }
//
//    @Transactional
//    @Test(expected = ConstraintViolationException.class)
//    public void saveShouldThrowExceptionWithNullUidPeripheralDevice() throws Exception {
//        baseDao.save(peripheralDeviceNullUid);
//    }
//
//    @Transactional
//    @Test(expected = ConstraintViolationException.class)
//    public void saveShouldThrowExceptionWithNullVendorPeripheralDevice() throws Exception {
//        baseDao.save(peripheralDeviceNullVendor);
//    }
//
//    @Transactional
//    @Test(expected = ConstraintViolationException.class)
//    public void saveShouldThrowExceptionWithNullDatePeripheralDevice() throws Exception {
//        baseDao.save(peripheralDeviceNullDate);
//    }
//
//    @Transactional
//    @Test(expected = ConstraintViolationException.class)
//    public void saveShouldThrowExceptionWithNullStatusPeripheralDevice() throws Exception {
//        baseDao.save(peripheralDeviceNullStatus);
//    }
//
//    private Date parseDate() {
//        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
//        Date date = null;
//        try {
//            date = format.parse("2015-12-20");
//        } catch (java.text.ParseException e) {
//            e.printStackTrace();
//        }
//
//        return date;
//    }
//}