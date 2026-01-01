package com.example.taskmanager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.Entity.User;
import com.example.taskmanager.Service.UserService;
import com.example.taskmanager.jwt.AuthResponse;
import com.example.taskmanager.jwt.JwtUtil;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Register
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    // ✅ JWT Login
    @PostMapping("/login")
    public AuthResponse login(@RequestBody User user) {
        User dbUser = userService.login(user.getEmail(), user.getPassword());

        if (dbUser == null) {
            throw new RuntimeException("Invalid Credentials");
        }

        String token = jwtUtil.generateToken(dbUser.getEmail());
        return new AuthResponse(token, dbUser.getEmail());
    }
}
