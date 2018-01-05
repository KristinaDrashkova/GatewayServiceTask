package com.musala.gateway.dao;

import com.musala.gateway.entities.BaseEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BaseDaoImpl implements BaseDao {
    @PersistenceContext(name = "gateway")
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public List<BaseEntity> findAll() {
        return em.createQuery("FROM BaseEntity ").getResultList();
    }

    @Override
    public BaseEntity findById(long id) {
        return em.find(BaseEntity.class, id);
    }

    @Override
    public void save(BaseEntity baseEntity) {
        em.persist(baseEntity);
    }
}
