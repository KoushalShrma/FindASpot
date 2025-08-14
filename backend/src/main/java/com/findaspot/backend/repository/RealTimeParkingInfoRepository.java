package com.findaspot.backend.repository;

import com.findaspot.backend.entity.RealTimeParkingInfo;
import com.findaspot.backend.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Real Time Parking Info Repository - yahan par real time parking info table ke liye database operations hain
 */
@Repository
public interface RealTimeParkingInfoRepository extends JpaRepository<RealTimeParkingInfo, Long> {

    // yahan par parking lot ke slots find karne ka method hai
    List<RealTimeParkingInfo> findByParkingLotOrderBySlotNo(ParkingLot parkingLot);

    // yahan par parking lot aur slot number se slot find karne ka method hai
    Optional<RealTimeParkingInfo> findByParkingLotAndSlotNo(ParkingLot parkingLot, Integer slotNo);

    // yahan par occupied slots find karne ka method hai
    List<RealTimeParkingInfo> findByParkingLotAndIsOccupied(ParkingLot parkingLot, Boolean isOccupied);

    // yahan par vehicle number se slot find karne ka method hai
    Optional<RealTimeParkingInfo> findByVehicleNo(String vehicleNo);
}