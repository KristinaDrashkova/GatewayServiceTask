package com.musala.gateway.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BaseDaoImpl<E> implements BaseDao<E> {
    @PersistenceContext(name = "gateway")
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public List<E> findAll() {
        return em.createQuery("FROM BaseEntity ").getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E>E findById(long id, String className) throws ClassNotFoundException {
        Class<E> currentClass = (Class<E>) Class.forName(className);
        return em.find(currentClass, id);
    }

    @Override
    public <E> void save(E entity) {
        em.persist(entity);
    }
}
