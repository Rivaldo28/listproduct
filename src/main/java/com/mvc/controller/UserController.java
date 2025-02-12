package com.mvc.controller;

import com.mvc.dto.LoginRequest;
import com.mvc.dto.LoginResponse;
import com.mvc.dto.UserRequest;
import com.mvc.dto.UserResponse;
import com.mvc.services.UserService;
import com.mvc.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

            return ResponseEntity.ok(loginResponse);

        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Error in login" + e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserRequest userRequest,
                                 @RequestHeader("Authorization") String jwt) {
        try {

            if(!this.jwtUtil.isTokenValid(jwt)){
                return ResponseEntity.badRequest().body("JWT invalid");
            }

            if(!this.jwtUtil.verifyRole(jwt, "SUPER")){
                return ResponseEntity.badRequest().body("your role does not meet the requirements");
            }

            UserResponse userResponse;
            userResponse = this.userService.create(userRequest);
            return ResponseEntity.ok(userResponse);

        }catch (Exception e) {
            return ResponseEntity.badRequest().body("error registering user" + e.getMessage());
        }
    }

}
