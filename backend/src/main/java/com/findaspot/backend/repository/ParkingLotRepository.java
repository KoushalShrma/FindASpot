package com.findaspot.backend.repository;

import com.findaspot.backend.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Parking Lot Repository - yahan par parking lots table ke liye database operations hain
 */
@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

    // yahan par parking ID se parking lot find karne ka method hai
    Optional<ParkingLot> findByParkingId(String parkingId);

    // yahan par parking ID exist karne ka check hai
    boolean existsByParkingId(String parkingId);

    // yahan par type ke basis par parking lots find karne ka method hai
    Iterable<ParkingLot> findByType(String type);
}