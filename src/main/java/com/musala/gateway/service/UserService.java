package com.musala.gateway.service;

import com.musala.gateway.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    void saveUser(User user);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
