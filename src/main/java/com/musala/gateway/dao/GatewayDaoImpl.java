package com.musala.gateway.dao;

import com.musala.gateway.entities.Gateway;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class GatewayDaoImpl implements GatewayDao {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("GatewayService");
    private EntityManager em = emf.createEntityManager();

    public GatewayDaoImpl() {
    }

    @Override
    public List<Gateway> findAll() {
        return em.createQuery("SELECT g FROM gateway AS g").getResultList();
    }

    @Override
    public Gateway findById(Integer id) {
        return em.find(Gateway.class, id);
    }

    @Override
    public void save(Gateway gateway) {
        em.persist(gateway);
    }
}
