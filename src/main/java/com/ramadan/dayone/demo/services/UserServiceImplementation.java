package com.ramadan.dayone.demo.services;

import com.ramadan.dayone.demo.domain.User;
import com.ramadan.dayone.demo.exceptions.AuthException;
import com.ramadan.dayone.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    @Autowired
    UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public User validateUser(String email, String password) throws AuthException {
        return null;
    }

    @Override
    public User registerUser(String name, String email, String password) throws AuthException {
        Pattern emailPatter = Pattern.compile("^(.+)@(.+)$");
        if(email!=null && !emailPatter.matcher(email.toLowerCase()).matches()) {
            Integer count = userRepository.getCountByEmail(email);
            if(count>0){
                throw new AuthException("Email already in use");
            }
            Integer userId = userRepository.create(name, email, password);
            return userRepository.findById(userId);
        }else{
            throw new AuthException("Invalid email");
        }

    }
}
