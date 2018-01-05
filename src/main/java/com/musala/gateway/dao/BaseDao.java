package com.musala.gateway.dao;

import com.musala.gateway.entities.BaseEntity;

import java.util.List;

public interface BaseDao {
    List<BaseEntity> findAll();

    BaseEntity findById(long id);

    void save(BaseEntity baseEntity);
}
