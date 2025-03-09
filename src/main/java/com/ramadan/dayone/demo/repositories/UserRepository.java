package com.ramadan.dayone.demo.repositories;

import com.ramadan.dayone.demo.domain.User;
import com.ramadan.dayone.demo.exceptions.AuthException;

public interface UserRepository {
    Integer create(String name, String email, String password) throws AuthException;
    User findByEmail(String email) throws AuthException;
    Integer getCountByEmail(String email) throws AuthException;
    User findById(Integer id) throws AuthException;
}
