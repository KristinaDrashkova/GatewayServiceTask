package com.musala.gateway.dao;

import com.musala.gateway.JpaConfig;
import com.musala.gateway.dto.GatewayAddDto;
import com.musala.gateway.entities.Gateway;
import com.musala.gateway.utils.ModelParser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {JpaConfig.class},
        loader = AnnotationConfigContextLoader.class)
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
    private GatewayAddDto gatewayAddDto =
            new GatewayAddDto("1331-1691-2320-1630-3127-2516", "Q", "192.168.3.24");
    private GatewayAddDto gatewayAddDtoInvalid =
            new GatewayAddDto("1331-1691-2320-1630-3127-2516", "Q", "1921.168.3.24");
    @PersistenceContext
    private EntityManager em;

    @Qualifier("gatewayDaoImpl")
    @Autowired
    private GatewayDao gatewayDao;

    @Transactional
    @Test
    public void findAllShouldWorkCorrectly() {
        List<Gateway> gateways = gatewayDao.findAll();
        Assert.assertEquals(0, gateways.size());
        em.persist(gatewayNormal);

        gateways = gatewayDao.findAll();
        Assert.assertTrue(gateways.contains(gatewayNormal));
        Assert.assertEquals(1, gateways.size());
    }

    @Transactional
    @Test
    public void findByIdShouldWorkCorrectly() throws ClassNotFoundException {
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
    @Test(expected = PersistenceException.class)
    public void saveShouldThrowExceptionWithRepeatingSerialNumber() throws Exception {
        gatewayDao.save(gatewayNormal);
        gatewayDao.save(gatewayWithRepeatingSerialNumber);
    }

    @Transactional
    @Test
    public void updateShouldWorkCorrectly() throws ClassNotFoundException {
        em.persist(gatewayNormal);
        Gateway gatewayFromDto = ModelParser.getInstance().map(gatewayAddDto, Gateway.class);
        gatewayDao.update(gatewayNormal, gatewayFromDto);
        Gateway gateway = gatewayDao.findAll().get(0);
        Assert.assertEquals(gateway, gatewayFromDto);
    }
    @Transactional
    @Test
    public void removeShouldWorkCorrectly() throws Exception {
        em.persist(gatewayNormal);

        gatewayDao.remove(gatewayNormal);
        List<Gateway> peripheralDevices = gatewayDao.findAll();
        Assert.assertEquals(0, peripheralDevices.size());
    }

    @Transactional
    @Test(expected = IllegalArgumentException.class)
    public void removeShouldThrowExceptionWithNoExistingNumber() throws Exception {
        em.persist(gatewayNormal);

        gatewayDao.remove(10);
    }

}