package com.musala.gateway.dao;

import java.util.List;

public interface BaseDao<K> {
    List<K> findAll();

    K findById(long id) throws ClassNotFoundException;

    void save(K entity);

    void remove(K entity);

    void update(K entityToUpdate, K newEntity);
}
