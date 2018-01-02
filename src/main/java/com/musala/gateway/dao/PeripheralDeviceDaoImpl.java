package com.musala.gateway.dao;

import com.musala.gateway.entities.PeripheralDevice;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class PeripheralDeviceDaoImpl implements PeripheralDeviceDao {

    @PersistenceContext(name = "gateway")
    private EntityManager em;

    @Override
    public List<PeripheralDevice> findAll() {
        try {
            return em.createQuery("FROM PeripheralDevice").getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    @Override
    public PeripheralDevice findByUid(int uid) {
        try {
            return em.find(PeripheralDevice.class, uid);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void save(PeripheralDevice peripheralDevice) {
        try {
            em.persist(peripheralDevice);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void remove(int uid) {
        try {
            PeripheralDevice peripheralDevice = findByUid(uid);
            em.remove(peripheralDevice);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
