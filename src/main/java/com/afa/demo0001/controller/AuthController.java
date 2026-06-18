package com.afa.demo0001.controller;


import com.afa.demo0001.dto.AuthRequest;
import com.afa.demo0001.model.User;
import com.afa.demo0001.repository.UserRepository;
import com.afa.demo0001.security.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody AuthRequest authRequest){
         if(userRepository.findByName(authRequest.getName()).isPresent()){
             return ResponseEntity.badRequest().body("Username is already taken!");
         }

         User newUser = new User();
         newUser.setName(authRequest.getName());
         newUser.setPassword(passwordEncoder.encode(authRequest.getPassword()));
         newUser.setRole("ROLE_ADMIN");

         userRepository.save(newUser);
         return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthRequest authRequest){
        Optional<User> userOpt = userRepository.findByName(authRequest.getName());

        if(userOpt.isPresent() && passwordEncoder.matches(authRequest.getPassword(), userOpt.get().getPassword())){
            String token = jwtUtils.generateToken(authRequest.getName());
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(401).body("Invalid username or password!");
    }
;}
