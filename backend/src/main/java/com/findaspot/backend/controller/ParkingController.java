package com.findaspot.backend.controller;

import com.findaspot.backend.dto.ApiResponse;
import com.findaspot.backend.dto.ParkingSlotDto;
import com.findaspot.backend.entity.ParkingLot;
import com.findaspot.backend.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Parking Controller - yahan par parking related API endpoints hain
 */
@RestController
@RequestMapping("/api/parking")
@CrossOrigin(origins = "http://localhost:3000")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    // yahan par parking lots ki list get karne ka endpoint hai
    @GetMapping("/lots")
    public ResponseEntity<ApiResponse<List<ParkingLot>>> getAllParkingLots() {
        try {
            List<ParkingLot> parkingLots = parkingService.getAllParkingLots();
            return ResponseEntity.ok(ApiResponse.success("Parking lots retrieved successfully", parkingLots));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Failed to retrieve parking lots"));
        }
    }

    // yahan par specific parking lot get karne ka endpoint hai
    @GetMapping("/lots/{id}")
    public ResponseEntity<ApiResponse<ParkingLot>> getParkingLotById(@PathVariable Long id) {
        try {
            Optional<ParkingLot> parkingLot = parkingService.getParkingLotById(id);
            if (parkingLot.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Parking lot found", parkingLot.get()));
            } else {
                return ResponseEntity.ok(ApiResponse.error("Parking lot not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Failed to retrieve parking lot"));
        }
    }

    // yahan par real-time parking slots get karne ka endpoint hai
    @GetMapping("/lots/{id}/slots")
    public ResponseEntity<ApiResponse<List<ParkingSlotDto>>> getRealTimeParkingInfo(@PathVariable Long id) {
        try {
            List<ParkingSlotDto> slots = parkingService.getRealTimeParkingInfo(id);
            return ResponseEntity.ok(ApiResponse.success("Parking slots retrieved successfully", slots));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Failed to retrieve parking slots"));
        }
    }

    // yahan par specific slot status get karne ka endpoint hai
    @GetMapping("/lots/{id}/slots/{slotNo}")
    public ResponseEntity<ApiResponse<ParkingSlotDto>> getSlotStatus(
            @PathVariable Long id, 
            @PathVariable Integer slotNo) {
        try {
            Optional<ParkingSlotDto> slot = parkingService.getSlotStatus(id, slotNo);
            if (slot.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Slot status retrieved", slot.get()));
            } else {
                return ResponseEntity.ok(ApiResponse.error("Slot not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Failed to retrieve slot status"));
        }
    }

    // yahan par available slots count get karne ka endpoint hai
    @GetMapping("/lots/{id}/available-count")
    public ResponseEntity<ApiResponse<Long>> getAvailableSlotCount(@PathVariable Long id) {
        try {
            long count = parkingService.getAvailableSlotCount(id);
            return ResponseEntity.ok(ApiResponse.success("Available slot count retrieved", count));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Failed to get available slot count"));
        }
    }

    // yahan par parking lot search karne ka endpoint hai
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ParkingLot>>> searchParkingLots(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String location) {
        try {
            List<ParkingLot> parkingLots;
            if (type != null) {
                parkingLots = (List<ParkingLot>) parkingService.getAllParkingLots()
                        .stream()
                        .filter(lot -> type.equalsIgnoreCase(lot.getType()))
                        .toList();
            } else {
                parkingLots = parkingService.getAllParkingLots();
            }
            
            return ResponseEntity.ok(ApiResponse.success("Search results retrieved", parkingLots));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Search failed"));
        }
    }

    // yahan par health check endpoint hai
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("Parking service is running"));
    }
}