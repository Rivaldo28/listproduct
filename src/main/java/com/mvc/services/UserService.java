package com.mvc.services;

import com.mvc.dao.UserRepository;
import com.mvc.dto.LoginRequest;
import com.mvc.dto.LoginResponse;
import com.mvc.dto.UserRequest;
import com.mvc.dto.UserResponse;
import com.mvc.model.User;
import com.mvc.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    public LoginResponse login(LoginRequest loginRequest) throws Exception {

        User user = userRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new Exception("invalid credential"));

        if(this.passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
        {
            return new LoginResponse(this.jwtUtil.generateToken(user.getRole()), "Login success");
        }
        else
        {
            throw new Exception("invalid credential");
        }

    }

    public UserResponse create(UserRequest userRequest) throws Exception
    {
        this.userRepository.findByUsername(userRequest.getUsername())
                            .ifPresent(user -> {
                                throw new RuntimeException("username already registered");
                            });
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(
                this.passwordEncoder.encode(userRequest.getPassword())
        );
        user.setRole(userRequest.getRole());
        user.setStatus(true);

        this.userRepository.save(user);

        return new UserResponse(user.getUsername(), user.getRole(), "user created");
    }

}
