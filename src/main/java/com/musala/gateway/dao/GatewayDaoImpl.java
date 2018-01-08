package com.musala.gateway.dao;

import com.musala.gateway.entities.Gateway;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public class GatewayDaoImpl extends BaseDaoImpl implements GatewayDao {
    @SuppressWarnings("unchecked")
    @Override
    public List<Gateway> findAll() {
        return super.findAll("Gateway");
    }

    @Override
    public Gateway findById(long id) throws ClassNotFoundException {
        return (Gateway) super.findById(id, "Gateway");
    }

    @Transactional
    public void update(long id, Object dto) throws ClassNotFoundException {
        super.update(id, dto, "Gateway");
    }
}
