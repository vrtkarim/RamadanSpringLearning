package com.vrtkarim.usermanagement.service;

import com.vrtkarim.usermanagement.entity.User;

public interface UserService {
    User createUser(User user) ;
    User login(String email, String password);
}
