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
    public List<E> findAll(String selectedClass) {
        return em.createQuery("FROM " + selectedClass).getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public E findById(long id, String className) throws ClassNotFoundException {
        Class<E> currentClass = (Class<E>) Class.forName("com.musala.gateway.entities." + className);
        return em.find(currentClass, id);
    }

    @Override
    public <E> void save(E entity) {
        em.persist(entity);
    }

    @Override
    public <E> void remove(E entity) {
        em.remove(entity);
    }

    @Override
    public <E> void update(E entityToUpdate, E newEntity) {
        em.remove(entityToUpdate);
        em.persist(newEntity);
    }
}
