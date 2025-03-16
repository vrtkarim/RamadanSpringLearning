package com.vrtkarim.usermanagement.controller;

import com.vrtkarim.usermanagement.entity.User;
import com.vrtkarim.usermanagement.repository.UserRepository;
import com.vrtkarim.usermanagement.service.JwtService;
import com.vrtkarim.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class Controller {
    private final UserService userService;
    private final JwtService jwtService;
    @Autowired
    public Controller(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Map<String, Object> map) {
        User user = new User();
        user.setName(map.get("name").toString());
        user.setPassword(map.get("password").toString());
        user.setEmail(map.get("email").toString());
        userService.createUser(user);


        return new ResponseEntity<>(jwtService.generateToken(user),HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> map) {
        String email = map.get("email");
        String password = map.get("password");
        User user = userService.login(email, password);
        return new ResponseEntity<>(user.getName()+" logged in successfully",HttpStatus.OK);
    }
    @GetMapping("/hi")
    public ResponseEntity<String> hi() {
        return new ResponseEntity<>("Hi",HttpStatus.I_AM_A_TEAPOT);
    }


}
