package com.musala.gateway.dao;

import com.musala.gateway.entities.PeripheralDevice;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class PeripheralDeviceDaoImpl extends BaseDaoImpl<PeripheralDevice> implements PeripheralDeviceDao {

    @PersistenceContext(name = "gateway")
    private EntityManager em;

    public PeripheralDeviceDaoImpl() {
        super(PeripheralDevice.class);
    }

    @Transactional
    @Override
    public void remove(PeripheralDevice peripheralDevice) {
        em.remove(peripheralDevice);
    }

}
