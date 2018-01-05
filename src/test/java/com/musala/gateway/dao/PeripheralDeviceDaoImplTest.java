package com.musala.gateway.dao;

import com.musala.gateway.JpaConfig;
import com.musala.gateway.entities.BaseEntity;
import com.musala.gateway.entities.PeripheralDevice;
import com.musala.gateway.entities.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {JpaConfig.class},
        loader = AnnotationConfigContextLoader.class)
public class PeripheralDeviceDaoImplTest {
    @Autowired
    private PeripheralDeviceDao peripheralDeviceDao;

    private BaseEntity peripheralDevice;

    @PersistenceContext
    private EntityManager em;

    @Before
    public void setUp() throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        Date date = format.parse("2015-12-20");
        peripheralDevice = new PeripheralDevice(1, "SteelSeries", date, Status.OFFLINE);
    }

    @Transactional
    @Test
    public void removeShouldWorkCorrectly() throws Exception {
        em.persist(peripheralDevice);

        peripheralDeviceDao.remove(peripheralDevice.getId());
        List<BaseEntity> baseEntities = peripheralDeviceDao.findAll();
        Assert.assertEquals(0, baseEntities.size());
    }

    @Transactional
    @Test(expected = IllegalArgumentException.class)
    public void removeShouldThrowExceptionWithNoExistingNumber() throws Exception {
        em.persist(peripheralDevice);

        peripheralDeviceDao.remove(10);
    }
}