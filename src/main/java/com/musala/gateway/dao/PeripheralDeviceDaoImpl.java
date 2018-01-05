package com.musala.gateway.dao;

import com.musala.gateway.entities.PeripheralDevice;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
@Repository
public class PeripheralDeviceDaoImpl extends BaseDaoImpl implements PeripheralDeviceDao {

    @PersistenceContext(name = "gateway")
    private EntityManager em;

    @Override
    public void remove(long id) throws ClassNotFoundException {
        PeripheralDevice peripheralDevice = (PeripheralDevice) findById(id, "PeripheralDevice");
        em.remove(peripheralDevice);
    }
}
