package com.findaspot.backend.controller;

import com.findaspot.backend.dto.ApiResponse;
import com.findaspot.backend.entity.ParkingLot;
import com.findaspot.backend.entity.User;
import com.findaspot.backend.repository.UserRepository;
import com.findaspot.backend.service.ParkingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Admin Controller - yahan par admin related API endpoints hain
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParkingService parkingService;

    // yahan par admin dashboard data get karne ka endpoint hai
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<Object>> getDashboardData() {
        try {
            // yahan par dashboard ke liye basic stats collect kar rahe hain
            long totalUsers = userRepository.count();
            long totalCustomers = userRepository.findByRole("customer").spliterator().getExactSizeIfKnown();
            List<ParkingLot> parkingLots = parkingService.getAllParkingLots();
            
            Object dashboardData = new Object() {
                public final long totalUsers = AdminController.this.userRepository.count();
                public final long totalCustomers = AdminController.this.userRepository.findByRole("customer")
                        .spliterator().getExactSizeIfKnown();
                public final long totalParkingLots = parkingLots.size();
                public final List<ParkingLot> recentParkingLots = parkingLots.stream().limit(5).toList();
            };
            
            return ResponseEntity.ok(ApiResponse.success("Dashboard data retrieved", dashboardData));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Failed to retrieve dashboard data"));
        }
    }

    // yahan par all users get karne ka endpoint hai
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", users));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Failed to retrieve users"));
        }
    }

    // yahan par naya parking lot create karne ka endpoint hai
    @PostMapping("/parking-lots")
    public ResponseEntity<ApiResponse<ParkingLot>> createParkingLot(@Valid @RequestBody ParkingLot parkingLot) {
        try {
            ParkingLot createdParkingLot = parkingService.createParkingLot(parkingLot);
            return ResponseEntity.ok(ApiResponse.success("Parking lot created successfully", createdParkingLot));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Failed to create parking lot: " + e.getMessage()));
        }
    }

    // yahan par user delete karne ka endpoint hai
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long userId) {
        try {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
                return ResponseEntity.ok(ApiResponse.success("User deleted successfully"));
            } else {
                return ResponseEntity.ok(ApiResponse.error("User not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Failed to delete user"));
        }
    }

    // yahan par reports endpoint hai
    @GetMapping("/reports")
    public ResponseEntity<ApiResponse<Object>> getReports() {
        try {
            // yahan par basic reports data create kar rahe hain
            Object reportData = new Object() {
                public final String message = "Reports feature coming soon";
                public final long totalUsers = userRepository.count();
                public final long totalParkingLots = parkingService.getAllParkingLots().size();
            };
            
            return ResponseEntity.ok(ApiResponse.success("Report data retrieved", reportData));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Failed to generate reports"));
        }
    }

    // yahan par admin health check endpoint hai
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("Admin service is running"));
    }
}