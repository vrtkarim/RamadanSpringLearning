package com.ramadan.dayone.demo.controller;


import com.ramadan.dayone.demo.domain.User;
import com.ramadan.dayone.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, Object> body) {
        String email = (String) body.get("email");
        String password = (String) body.get("password");
        String name = (String) body.get("name");
        User user = userService.registerUser(name,email, password);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Registration successful");
        return new ResponseEntity<>(map, HttpStatus.CREATED);

    }

}
