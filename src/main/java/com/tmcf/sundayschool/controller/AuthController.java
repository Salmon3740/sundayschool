package com.tmcf.sundayschool.controller;

import com.tmcf.sundayschool.dto.request.LoginRequest;
import com.tmcf.sundayschool.dto.request.RegisterRequest;
import com.tmcf.sundayschool.dto.response.AuthResponse;
import com.tmcf.sundayschool.dto.response.ApiResponse;
import com.tmcf.sundayschool.entity.User;
import com.tmcf.sundayschool.exception.InvalidCredentialsException;
import com.tmcf.sundayschool.security.JwtUtils;
import com.tmcf.sundayschool.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService,
            JwtUtils jwtUtils,
            AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Register a new user.
     * If secretKey is provided and valid → registers as TEACHER.
     * Otherwise → registers as STUDENT.
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User savedUser;

        // If secretKey is provided, attempt teacher registration
        if (request.getSecretKey() != null && !request.getSecretKey().isBlank()) {
            savedUser = userService.registerTeacher(user, request.getSecretKey());
        } else {
            savedUser = userService.registerStudent(user);
        }

        // Generate JWT token for the new user
        String token = jwtUtils.generateToken(
                savedUser.getUsername(),
                savedUser.getRole().name());

        AuthResponse authResponse = new AuthResponse(
                token,
                savedUser.getUsername(),
                savedUser.getRole().name());

        return ResponseEntity.ok(ApiResponse.success("Registration successful", authResponse));
    }

    /**
     * Login with username and password.
     * Returns JWT token with role info.
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));
            org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        // Fetch user to get role
        User user = userService.findByUsername(request.getUsername());

        String token = jwtUtils.generateToken(
                user.getUsername(),
                user.getRole().name());

        AuthResponse authResponse = new AuthResponse(
                token,
                user.getUsername(),
                user.getRole().name());

        return ResponseEntity.ok(ApiResponse.success("Login successful", authResponse));
    }
}
