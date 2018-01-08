package com.musala.gateway.dao;

public interface GatewayDao extends BaseDao {
    void update(long id, Object dto) throws ClassNotFoundException;
}
