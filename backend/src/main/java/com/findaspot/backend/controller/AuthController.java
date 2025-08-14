package com.findaspot.backend.controller;

import com.findaspot.backend.dto.*;
import com.findaspot.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication Controller - yahan par authentication related API endpoints hain
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

    // yahan par user registration endpoint hai
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@Valid @RequestBody RegisterRequest request) {
        ApiResponse<String> response = authService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    // yahan par OTP verification endpoint hai
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<String>> verifyOtp(@Valid @RequestBody OtpVerificationRequest request) {
        ApiResponse<String> response = authService.verifyOtp(request);
        return ResponseEntity.ok(response);
    }

    // yahan par user login endpoint hai
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> loginUser(@Valid @RequestBody LoginRequest request) {
        ApiResponse<JwtResponse> response = authService.loginUser(request);
        return ResponseEntity.ok(response);
    }

    // yahan par OTP resend endpoint hai
    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponse<String>> resendOtp(@RequestParam String email) {
        ApiResponse<String> response = authService.resendOtp(email);
        return ResponseEntity.ok(response);
    }

    // yahan par health check endpoint hai
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("Authentication service is running"));
    }
}