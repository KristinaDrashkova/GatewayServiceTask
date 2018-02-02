package com.musala.gateway.dao;

import com.musala.gateway.entities.User;

public interface UserDao extends BaseDao<User>{
    User findByUsername(String username);
}
