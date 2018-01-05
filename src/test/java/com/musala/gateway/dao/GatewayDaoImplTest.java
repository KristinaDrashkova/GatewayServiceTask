//package com.musala.gateway.dao;
//
//import com.musala.gateway.JpaConfig;
//import com.musala.gateway.entities.Gateway;
//import com.musala.gateway.entities.PeripheralDevice;
//import com.musala.gateway.entities.Status;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.transaction.Transactional;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Locale;
//import java.util.Set;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(
//        classes = {JpaConfig.class},
//        loader = AnnotationConfigContextLoader.class)
//public class GatewayDaoImplTest {
//    @Autowired
//    private GatewayDao gatewayDao;
//
//    @PersistenceContext
//    private EntityManager em;
//
//    private Gateway gateway;
//
//    @Before
//    public void setUp() throws ParseException {
//        gateway = new Gateway("1245-1234-1234-1235", "name", "192.168.3.24");
//        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
//        PeripheralDevice peripheralDevice =
//                new PeripheralDevice(1, "SteelSeries", format.parse("2015-12-20"), Status.OFFLINE, gateway);
//        Set<PeripheralDevice> peripheralDevices = new LinkedHashSet<>();
//        peripheralDevices.add(peripheralDevice);
//        gateway.setPeripheralDevices(peripheralDevices);
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void findAllShouldWorkCorrectly() {
//        List<Gateway> gateways = gatewayDao.findAll();
//        Assert.assertEquals(0, gateways.size());
//        em.persist(gateway);
//
//        gateways = gatewayDao.findAll();
//        Assert.assertEquals(gateway.getId(), gateways.get(0).getId());
//        Assert.assertEquals(1, gateways.size());
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void findByIdShouldWorkCorrectly() {
//        em.persist(gateway);
//
//        Gateway gatewayDb = gatewayDao.findById(gateway.getId());
//        Gateway gatewayNull = gatewayDao.findById(10);
//        Assert.assertEquals(gateway, gatewayDb);
//        Assert.assertEquals(null, gatewayNull);
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void saveShouldWorkCorrectly() {
//        gatewayDao.save(gateway);
//
//        Gateway gatewayDb = gatewayDao.findById(gateway.getId());
//        Assert.assertEquals(gateway, gatewayDb);
//    }
//
//    @Test//(expected = IllegalArgumentException.class)
//    @Transactional
//    @Rollback
//    public void saveShouldThrowExceptionWithNull() {
//        gateway = new Gateway("1245-1234-1234-1235", null, "192.168.3.24");
//        gatewayDao.save(gateway);
//
//    }
//}