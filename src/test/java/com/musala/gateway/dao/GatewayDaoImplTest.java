package com.musala.gateway.dao;

import com.musala.gateway.entities.Gateway;
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
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GatewayDaoImplTest {
    private Gateway gatewayNormal =
            new Gateway("1245-1234-1234-1235", "name", "192.168.3.24");
    private Gateway gatewayWithRepeatingSerialNumber =
            new Gateway("1245-1234-1234-1235", "name", "192.168.3.24");
    private Gateway gatewayNullSerialNumber =
            new Gateway(null, "name", "192.168.3.24");
    private Gateway gatewayNullName =
            new Gateway("1245-1234-1234-1236", null, "192.168.3.24");
    private Gateway gatewayNullAddress =
            new Gateway("1245-1234-1234-1237", "name", null);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private GatewayDao gatewayDao;

    @Transactional
    @Test
    public void findAllShouldWorkCorrectly() {
        List<Gateway> gateways = gatewayDao.findAll();
        Assert.assertEquals(2, gateways.size());
        em.persist(gatewayNormal);

        gateways = gatewayDao.findAll();
        Assert.assertTrue(gateways.contains(gatewayNormal));
        Assert.assertEquals(3, gateways.size());
    }

    @Transactional
    @Test
    public void findByIdShouldWorkCorrectly() {
        em.persist(gatewayNormal);

        Gateway gatewayDb = gatewayDao.findById(gatewayNormal.getId());
        Gateway gatewayNull = gatewayDao.findById(10);
        Assert.assertEquals(gatewayNormal, gatewayDb);
        Assert.assertEquals(null, gatewayNull);
    }

    @Transactional
    @Test
    public void saveShouldWorkCorrectly() throws Exception {
        gatewayDao.save(gatewayNormal);

        Gateway gateway = gatewayDao.findById(gatewayNormal.getId());
        Assert.assertEquals(gatewayNormal, gateway);
    }

    @Transactional
    @Test(expected = ConstraintViolationException.class)
    public void saveShouldThrowExceptionWithNullAddress() throws Exception {
        gatewayDao.save(gatewayNullAddress);
    }

    @Transactional
    @Test(expected = ConstraintViolationException.class)
    public void saveShouldThrowExceptionWithNullName() throws Exception {
        gatewayDao.save(gatewayNullName);
    }

    @Transactional
    @Test(expected = ConstraintViolationException.class)
    public void saveShouldThrowExceptionWithNullSerialNumber() throws Exception {
        gatewayDao.save(gatewayNullSerialNumber);
    }

    @Transactional
    @Test(expected = DataIntegrityViolationException.class)
    public void saveShouldThrowExceptionWithRepeatingSerialNumber() throws Exception {
        gatewayDao.save(gatewayNormal);
        gatewayDao.save(gatewayWithRepeatingSerialNumber);
    }

    @Transactional
    @Test
    public void removeShouldWorkCorrectly() throws Exception {
        em.persist(gatewayNormal);

        gatewayDao.remove(gatewayNormal);
        List<Gateway> gateways = gatewayDao.findAll();
        Assert.assertEquals(2, gateways.size());
    }

}