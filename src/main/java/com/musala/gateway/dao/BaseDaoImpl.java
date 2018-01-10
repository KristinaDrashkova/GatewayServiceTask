package com.musala.gateway.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class BaseDaoImpl<K> implements BaseDao<K> {
    @PersistenceContext(name = "gateway")
    private EntityManager em;

    @Override
    public List<K> findAll(Class<K> entityClass) {
        return em.createQuery("FROM " + entityClass.getSimpleName()).getResultList();
    }

    @Override
    public K findById(long id, Class<K> entityClass) throws ClassNotFoundException {
        return em.find(entityClass, id);
    }

    @Override
    public void save(K entity) {
        em.persist(entity);
    }

    @Override
    public void remove(K entity) {
        em.remove(entity);
    }

    @Override
    public void update(K entityToUpdate, K newEntity) {
        em.remove(entityToUpdate);
        em.persist(newEntity);
    }
}
