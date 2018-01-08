package com.musala.gateway.dao;

import java.util.List;

public interface BaseDao<Е> {
    List<Е> findAll(String selectedClass);

    <E> E findById(long id, String className) throws ClassNotFoundException;

    <E> void save(E entity);

    <E> void update(long id, E dto, String classToMap) throws ClassNotFoundException;
}
