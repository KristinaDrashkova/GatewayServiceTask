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

    @SuppressWarnings("unchecked")
    @Override
    public List<PeripheralDevice> findAll() {
        return super.findAll("PeripheralDevice");
    }

    @Transactional
    @Override
    public void remove(long id) throws ClassNotFoundException {
        PeripheralDevice peripheralDevice = (PeripheralDevice) findById(id, "PeripheralDevice");
        remove(peripheralDevice);
    }

    @Transactional
    @Override
    public void remove(PeripheralDevice peripheralDevice) {
        em.remove(peripheralDevice);
    }

    @Transactional
    public void update(long id, Object dto) throws ClassNotFoundException {
        super.update(id, dto, "PeripheralDevice");
    }

    @Override
    public PeripheralDevice findById(long id) throws ClassNotFoundException {
        return (PeripheralDevice) super.findById(id, "PeripheralDevice");
    }
}
