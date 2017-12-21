package com.musala.gateway.dao;

import com.musala.gateway.entities.PeripheralDevice;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class PeripheralDeviceDaoImpl implements PeripheralDeviceDao {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("GatewayService");
    private EntityManager em = emf.createEntityManager();

    public PeripheralDeviceDaoImpl() {
    }

    @Override
    public List<PeripheralDevice> findAll() {
        return em.createQuery("SELECT pd FROM peripheral_device AS pd").getResultList();
    }

    @Override
    public PeripheralDevice findByUid(int uid) {
        return em.find(PeripheralDevice.class, uid);
    }

    @Override
    public void save(PeripheralDevice peripheralDevice) {
        em.persist(peripheralDevice);
    }

    @Override
    public void remove(int uid) {

    }
}
