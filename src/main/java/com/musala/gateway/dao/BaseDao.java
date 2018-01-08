package com.musala.gateway.dao;

import java.util.List;

public interface BaseDao<Е> {
    List<Е> findAll(String selectedClass);

    <E> E findById(long id, String className) throws ClassNotFoundException;

    <E> void save(E entity);

    <M> void update(long id, M dto, String classToMap) throws ClassNotFoundException;
}
