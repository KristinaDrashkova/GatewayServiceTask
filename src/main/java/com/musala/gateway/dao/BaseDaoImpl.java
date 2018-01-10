package com.musala.gateway.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BaseDaoImpl<E> implements BaseDao<E> {
    @PersistenceContext(name = "gateway")
    private EntityManager em;

    @Override
    public List findAll(Class<E> entityClass) {
        return em.createQuery("FROM " + entityClass.getSimpleName()).getResultList();
    }

    @Override
    public E findById(long id, Class<E> entityClass) throws ClassNotFoundException {
        return em.find(entityClass, id);
    }

    @Override
    public void save(E entity) {
        em.persist(entity);
    }

    @Override
    public void remove(E entity) {
        em.remove(entity);
    }

    @Override
    public void update(E entityToUpdate, E newEntity) {
        em.remove(entityToUpdate);
        em.persist(newEntity);
    }
}
