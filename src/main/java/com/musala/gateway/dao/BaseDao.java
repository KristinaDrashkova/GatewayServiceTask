package com.musala.gateway.dao;

import java.util.List;

public interface BaseDao<E> {
    List findAll(Class<E> entityClass);

    E findById(long id, Class<E> entityClass) throws ClassNotFoundException;

    void save(E entity);

    void remove(E entity);

    void update(E entityToUpdate, E newEntity);
}
