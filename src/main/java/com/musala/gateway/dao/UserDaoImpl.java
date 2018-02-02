package com.musala.gateway.dao;

import com.musala.gateway.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    @PersistenceContext(name = "gateway")
    private EntityManager em;

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User findByUsername(String username) {
        return (User) em.createQuery(
                "SELECT u FROM User u WHERE u.username LIKE :username")
                .setParameter("username", username)
                .getResultList();
    }
}
