package com.ramadan.dayone.demo.service;

import com.ramadan.dayone.demo.dto.UserDto;
import com.ramadan.dayone.demo.dto.UserMapper;
import com.ramadan.dayone.demo.entity.User;
import com.ramadan.dayone.demo.exception.UserNotFoundException;
import com.ramadan.dayone.demo.exception.WrongPasswordException;
import com.ramadan.dayone.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    @Autowired
    UserServiceImplementation(UserRepository _userRepository) {
        this.userRepository = _userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }
    public UserDto login(String email, String password) {
        try{
            User user = userRepository.findByEmail(email);
            if(user.getPassword().equals(password)){
                return UserMapper.mapToUserDto(user);
            }else{
                throw new WrongPasswordException("Wrong password");
            }
        }catch (Exception e){
            throw new UserNotFoundException(e.getMessage());
        }


    }
}
