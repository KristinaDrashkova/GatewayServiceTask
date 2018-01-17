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
    public void remove(long id) {
        PeripheralDevice peripheralDevice = findById(id);
        em.remove(peripheralDevice);
    }

    @Override
    public PeripheralDevice findByUid(int uid) {
        return (PeripheralDevice) em.createQuery("SELECT pd FROM PeripheralDevice pd WHERE pd.uid LIKE " + uid).getResultList().get(0);
    }

    @Transactional
    @Override
    public void remove(PeripheralDevice peripheralDevice) {
        em.remove(peripheralDevice);
    }
}
