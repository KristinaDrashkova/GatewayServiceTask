package com.musala.gateway.dao;

import com.musala.gateway.entities.PeripheralDevice;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PeripheralDeviceDaoImpl extends BaseDaoImpl implements PeripheralDeviceDao {

    @PersistenceContext(name = "gateway")
    private EntityManager em;

    public PeripheralDeviceDaoImpl() {
        super(PeripheralDevice.class);
    }

    @Override
    public List<PeripheralDevice> findAll() {
        return super.findAll();
    }

    @Transactional
    @Override
    public void remove(long id) throws ClassNotFoundException {
        PeripheralDevice peripheralDevice = findById(id);
        em.remove(peripheralDevice);
    }

    @Transactional
    @Override
    public void remove(PeripheralDevice peripheralDevice) {
        em.remove(peripheralDevice);
    }

    @Override
    public PeripheralDevice findById(long id) throws ClassNotFoundException {
        return (PeripheralDevice) super.findById(id);
    }
}
