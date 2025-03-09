package com.ramadan.dayone.demo.services;

import com.ramadan.dayone.demo.domain.User;
import com.ramadan.dayone.demo.exceptions.AuthException;


import java.util.List;

public interface UserService {
    User validateUser(String email,  String password) throws AuthException;
    User registerUser(String name, String email, String password) throws AuthException;

}
