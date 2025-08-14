package com.findaspot.backend.service;

import com.findaspot.backend.dto.*;
import com.findaspot.backend.entity.CustomerProfile;
import com.findaspot.backend.entity.User;
import com.findaspot.backend.repository.CustomerProfileRepository;
import com.findaspot.backend.repository.UserRepository;
import com.findaspot.backend.security.JwtUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Authentication Service - yahan par user authentication related operations hain
 */
@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    // OTP storage - yahan par temporary OTP data store karte hain
    private final Map<String, OtpData> otpStorage = new HashMap<>();

    // yahan par user registration ka method hai
    public ApiResponse<String> registerUser(RegisterRequest request) {
        try {
            // yahan par email already exist karne ka check hai
            if (userRepository.existsByEmail(request.getEmail())) {
                return ApiResponse.error("Email already registered");
            }

            // yahan par OTP generate kar rahe hain
            String otp = emailService.generateOtp();

            // yahan par naya user create kar rahe hain
            User user = new User();
            user.setRole(request.getRole());
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setOtp(otp);
            user.setIsVerified(false);

            User savedUser = userRepository.save(user);

            // yahan par customer profile create kar rahe hain agar role customer hai
            if ("customer".equals(request.getRole())) {
                CustomerProfile profile = new CustomerProfile();
                profile.setUser(savedUser);
                profile.setName(request.getName());
                profile.setPhoneNo(request.getPhoneNo());
                profile.setVehicleNo(request.getVehicleNo());
                profile.setAge(request.getAge());
                profile.setGender(request.getGender());
                profile.setAddress(request.getAddress());
                profile.setCity(request.getCity());
                profile.setState(request.getState());

                customerProfileRepository.save(profile);
            }

            // yahan par OTP storage mein store kar rahe hain rate limiting ke liye
            otpStorage.put(request.getEmail(), new OtpData(otp, System.currentTimeMillis(), 0));

            // yahan par OTP email bhej rahe hain
            emailService.sendOtpEmail(request.getEmail(), request.getName(), otp);

            return ApiResponse.success("Registration successful! Check your email for OTP verification.");

        } catch (MessagingException e) {
            return ApiResponse.error("Failed to send verification email. Please try again.");
        } catch (Exception e) {
            return ApiResponse.error("Registration failed. Please try again.");
        }
    }

    // yahan par OTP verify karne ka method hai
    public ApiResponse<String> verifyOtp(OtpVerificationRequest request) {
        try {
            Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
            if (userOpt.isEmpty()) {
                return ApiResponse.error("User not found");
            }

            User user = userOpt.get();
            
            // yahan par OTP check kar rahe hain
            if (user.getOtp() == null || !user.getOtp().equals(request.getOtp())) {
                return ApiResponse.error("Invalid OTP");
            }

            // yahan par user ko verified mark kar rahe hain
            user.setIsVerified(true);
            user.setOtp(null); // Clear OTP after verification
            userRepository.save(user);

            // yahan par OTP storage se remove kar rahe hain
            otpStorage.remove(request.getEmail());

            // yahan par welcome email bhej rahe hain
            emailService.sendWelcomeEmail(request.getEmail(), user.getName());

            return ApiResponse.success("Email verified successfully! You can now log in.");

        } catch (MessagingException e) {
            return ApiResponse.error("Verification successful but failed to send welcome email.");
        } catch (Exception e) {
            return ApiResponse.error("OTP verification failed. Please try again.");
        }
    }

    // yahan par user login ka method hai
    public ApiResponse<JwtResponse> loginUser(LoginRequest request) {
        try {
            // yahan par user ko find kar rahe hain
            Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
            if (userOpt.isEmpty()) {
                return ApiResponse.error("User not found");
            }

            User user = userOpt.get();

            // yahan par verified check kar rahe hain
            if (!user.getIsVerified()) {
                return ApiResponse.error("Please verify your email first");
            }

            // yahan par authentication kar rahe hain
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // yahan par JWT token generate kar rahe hain
            String jwt = jwtUtils.generateJwtToken(authentication);

            JwtResponse jwtResponse = new JwtResponse(
                    jwt,
                    user.getUserId(),
                    user.getEmail(),
                    user.getName(),
                    user.getRole()
            );

            return ApiResponse.success("Login successful", jwtResponse);

        } catch (Exception e) {
            return ApiResponse.error("Invalid credentials");
        }
    }

    // yahan par OTP resend karne ka method hai
    public ApiResponse<String> resendOtp(String email) {
        try {
            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isEmpty()) {
                return ApiResponse.error("User not found");
            }

            User user = userOpt.get();

            if (user.getIsVerified()) {
                return ApiResponse.error("User is already verified");
            }

            // yahan par rate limiting check kar rahe hain
            OtpData otpData = otpStorage.get(email);
            if (otpData != null) {
                long timeDiff = System.currentTimeMillis() - otpData.timestamp;
                if (timeDiff < 60000) { // 1 minute wait
                    return ApiResponse.error("Please wait before requesting a new OTP");
                }
                if (otpData.attempts >= 3) {
                    return ApiResponse.error("Maximum OTP attempts reached. Please try again later.");
                }
            }

            // yahan par nayi OTP generate kar rahe hain
            String newOtp = emailService.generateOtp();
            user.setOtp(newOtp);
            userRepository.save(user);

            // yahan par OTP storage update kar rahe hain
            int attempts = otpData != null ? otpData.attempts + 1 : 1;
            otpStorage.put(email, new OtpData(newOtp, System.currentTimeMillis(), attempts));

            // yahan par OTP email bhej rahe hain
            emailService.sendOtpEmail(email, user.getName(), newOtp);

            return ApiResponse.success("New OTP sent to your email");

        } catch (MessagingException e) {
            return ApiResponse.error("Failed to send OTP email");
        } catch (Exception e) {
            return ApiResponse.error("Failed to resend OTP");
        }
    }

    // yahan par OTP data store karne ke liye inner class hai
    private static class OtpData {
        String otp;
        long timestamp;
        int attempts;

        OtpData(String otp, long timestamp, int attempts) {
            this.otp = otp;
            this.timestamp = timestamp;
            this.attempts = attempts;
        }
    }
}