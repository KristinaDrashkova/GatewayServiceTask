package com.musala.gateway.dao;

import com.musala.gateway.entities.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class BaseDaoImpl<K extends BaseEntity> implements BaseDao<K> {
    @PersistenceContext(name = "gateway")
    private EntityManager em;
    private Class<K> entityClass;

    public BaseDaoImpl(Class<K> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public List<K> findAll() {
        return em.createQuery("FROM " + entityClass.getSimpleName(), entityClass).getResultList();
    }

    @Override
    public K findById(long id) {
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

}
