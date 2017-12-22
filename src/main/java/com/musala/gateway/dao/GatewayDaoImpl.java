package com.musala.gateway.dao;

import com.musala.gateway.entities.Gateway;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class GatewayDaoImpl implements GatewayDao {

    @PersistenceUnit(unitName = "gateway")
    private EntityManagerFactory emf;

    @Override
    public List<Gateway> findAll() {
        EntityManager em = this.emf.createEntityManager();
        try {
            return em.createQuery("FROM Gateway").getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    @Override
    public Gateway findById(Integer id) {
        EntityManager em = this.emf.createEntityManager();
        try {
            return em.find(Gateway.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void save(Gateway gateway) {
        EntityManager em = this.emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(gateway);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
