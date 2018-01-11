package com.musala.gateway.dao;

import com.musala.gateway.entities.BaseEntity;

import java.util.List;

public interface BaseDao<K extends BaseEntity> {
    List<K> findAll();

    K findById(long id);

    void save(K entity);

    void remove(K entity);
}
