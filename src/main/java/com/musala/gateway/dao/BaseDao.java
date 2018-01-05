package com.musala.gateway.dao;

import java.util.List;

public interface BaseDao<Е> {
    List<Е> findAll();

    <E>E findById(long id, String className) throws ClassNotFoundException;

    <E>void save(E entity);
}
