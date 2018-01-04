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

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
    @Override
    public PeripheralDevice findByUid(int uid) {
        try {
            Query query = em.createQuery("FROM PeripheralDevice WHERE uid = :uid");
            query.setParameter("uid", uid);
            return (PeripheralDevice) query.getResultList().get(0);
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
