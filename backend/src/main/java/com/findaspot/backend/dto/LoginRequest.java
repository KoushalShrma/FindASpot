package com.findaspot.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Login Request DTO - yahan par user login ke liye request data hai
 */
public class LoginRequest {

    @Email(message = "Valid email is required")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    // yahan par default constructor hai
    public LoginRequest() {}

    // yahan par constructor with parameters hai
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // yahan par getters aur setters hain
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}