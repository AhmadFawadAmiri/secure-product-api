package com.afa.demo0001.controller;


import com.afa.demo0001.dto.AuthRequest;
import com.afa.demo0001.model.Product;
import com.afa.demo0001.model.User;
import com.afa.demo0001.repository.UserRepository;
import com.afa.demo0001.security.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@Valid @RequestBody AuthRequest authRequest){
         if(userRepository.findByName(authRequest.getName()).isPresent()){
             return ResponseEntity.badRequest().body("Username is already taken!");
         }

         User newUser = new User();
         newUser.setName(authRequest.getName());
         newUser.setPassword(passwordEncoder.encode(authRequest.getPassword()));
         if(authRequest.getRole() != null) {
             newUser.setRole(authRequest.getRole());
         }else {
             newUser.setRole("ROLE_ADMIN");
         }


         userRepository.save(newUser);
         return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthRequest authRequest){
        Optional<User> userOpt = userRepository.findByName(authRequest.getName());

        if(userOpt.isPresent() && passwordEncoder.matches(authRequest.getPassword(), userOpt.get().getPassword())){
            String token = jwtUtils.generateToken(authRequest.getName());
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(401).body("Invalid username or password!");
    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("User not found with ID " + id));
        String currentLoggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if(user.getName().equals(currentLoggedUser)){
            throw new IllegalArgumentException("You cannot delete your own account!");
        }
        userRepository.delete(user);
    }
;}
