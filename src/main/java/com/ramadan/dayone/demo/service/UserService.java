package com.ramadan.dayone.demo.service;

import com.ramadan.dayone.demo.entity.User;

public interface UserService {
    User createUser(User user) ;
    User login(String email, String password);
}
