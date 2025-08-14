package com.findaspot.backend.repository;

import com.findaspot.backend.entity.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Customer Profile Repository - yahan par customer profile table ke liye database operations hain
 */
@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {

    // yahan par user ID se profile find karne ka method hai
    Optional<CustomerProfile> findByUserId(Long userId);

    // yahan par vehicle number se profile find karne ka method hai
    Optional<CustomerProfile> findByVehicleNo(String vehicleNo);
}