package com.musala.gateway.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@Repository
public class GatewayDaoImpl extends BaseDaoImpl implements GatewayDao {
    @PersistenceContext(name = "gateway")
    private EntityManager em;

    @Transactional
    public void update(long id, Object dto) throws ClassNotFoundException {
        super.update(id, dto, "Gateway");
    }
}
