package com.vrtkarim.usermanagement.service;

import com.vrtkarim.usermanagement.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    User createUser(User user) ;
    User login(String email, String password);
    Optional<User> findByEmail(String email);
}
