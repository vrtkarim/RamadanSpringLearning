package com.ramadan.dayone.demo.service;

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
    public User createUser(User user) {

        User savedUser = userRepository.save(user);
        return savedUser;
    }
    public User login(String email, String password) {
        try{
            User user = userRepository.findByEmail(email);
            if(user.getPassword().equals(password)){
                return user;
            }else{
                throw new WrongPasswordException("Wrong password");
            }
        }catch (Exception e){
            throw new UserNotFoundException(e.getMessage());
        }


    }
}
