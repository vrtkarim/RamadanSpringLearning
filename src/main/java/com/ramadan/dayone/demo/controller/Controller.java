package com.ramadan.dayone.demo.controller;

import com.ramadan.dayone.demo.Constants;
import com.ramadan.dayone.demo.dto.UserDto;
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
    public ResponseEntity<Map<String, String>> create(@RequestBody UserDto user) {
        UserDto saved =userService.createUser(user);

        return new ResponseEntity<>(generatJWTToken(saved),HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> map) {
        String email = map.get("email");
        String password = map.get("password");
        UserDto user = userService.login(email, password);
        return new ResponseEntity<>(user.getName()+" logged in successfully",HttpStatus.OK);
    }
    private Map<String, String> generatJWTToken(UserDto user) {
        Map<String, String> map = new HashMap<>();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.api_key).claim("email", user.getEmail()).compact();
        map.put("token", token);

        return map;
    }
}
