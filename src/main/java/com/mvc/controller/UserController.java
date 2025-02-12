package com.mvc.controller;

import com.mvc.dto.LoginRequest;
import com.mvc.dto.LoginResponse;
import com.mvc.dto.UserRequest;
import com.mvc.dto.UserResponse;
import com.mvc.services.UserService;
import com.mvc.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    private final JWTUtil jwtUtil = new JWTUtil();


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        try{
            LoginResponse loginResponse;
            loginResponse = this.userService.login(loginRequest);

            return ResponseEntity.ok(loginRequest);

        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Error in login" + e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserRequest userRequest) {
        try {

            UserResponse userResponse;
            userResponse = this.userService.create(userRequest);
            return ResponseEntity.ok(userResponse);

        }catch (Exception e) {
            return ResponseEntity.badRequest().body("error registering user" + e.getMessage());
        }
    }

}
