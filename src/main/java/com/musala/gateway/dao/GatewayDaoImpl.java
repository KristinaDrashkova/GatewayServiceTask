package com.musala.gateway.dao;

import com.musala.gateway.entities.Gateway;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class GatewayDaoImpl implements GatewayDao {

    @PersistenceContext(name = "gateway")
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public List<Gateway> findAll() {
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
        try {
            em.persist(gateway);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
