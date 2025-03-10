package com.ramadan.dayone.demo.service;

import com.ramadan.dayone.demo.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto) ;
    UserDto login(String email, String password);
}
