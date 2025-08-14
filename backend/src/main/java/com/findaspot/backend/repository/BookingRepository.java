package com.findaspot.backend.repository;

import com.findaspot.backend.entity.Booking;
import com.findaspot.backend.entity.User;
import com.findaspot.backend.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Booking Repository - yahan par bookings table ke liye database operations hain
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // yahan par user ke bookings find karne ka method hai
    List<Booking> findByUserOrderByCreatedAtDesc(User user);

    // yahan par parking lot ke bookings find karne ka method hai
    List<Booking> findByParkingLot(ParkingLot parkingLot);

    // yahan par status ke basis par bookings find karne ka method hai
    List<Booking> findByStatus(String status);

    // yahan par user aur status ke basis par bookings find karne ka method hai
    List<Booking> findByUserAndStatus(User user, String status);
}