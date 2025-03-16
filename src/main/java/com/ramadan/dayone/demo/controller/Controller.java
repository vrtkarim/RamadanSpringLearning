package com.ramadan.dayone.demo.controller;

import com.ramadan.dayone.demo.entity.User;
import com.ramadan.dayone.demo.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class Controller {
    private final UserService userService;
    @Autowired
    public Controller(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Map<String, Object> map) {
        User user = new User();
        user.setName(map.get("name").toString());
        user.setPassword(map.get("password").toString());
        user.setEmail(map.get("email").toString());
        userService.createUser(user);

        return new ResponseEntity<>("User created successfully",HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> map) {
        String email = map.get("email");
        String password = map.get("password");
        User user = userService.login(email, password);
        return new ResponseEntity<>(user.getName()+" logged in successfully",HttpStatus.OK);
    }


}
