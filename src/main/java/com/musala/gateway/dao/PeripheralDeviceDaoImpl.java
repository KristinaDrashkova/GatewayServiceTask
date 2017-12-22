package com.musala.gateway.dao;

import com.musala.gateway.entities.PeripheralDevice;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import java.util.List;

@Repository
public class PeripheralDeviceDaoImpl implements PeripheralDeviceDao {

    @PersistenceUnit(unitName = "gateway")
    private EntityManagerFactory emf;

    @Override
    public List<PeripheralDevice> findAll() {
        EntityManager em = this.emf.createEntityManager();
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
        EntityManager em = this.emf.createEntityManager();
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
        EntityManager em = this.emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(peripheralDevice);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void remove(int uid) {
        EntityManager em = this.emf.createEntityManager();
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
