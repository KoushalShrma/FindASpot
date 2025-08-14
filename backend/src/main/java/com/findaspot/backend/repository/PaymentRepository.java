package com.findaspot.backend.repository;

import com.findaspot.backend.entity.Payment;
import com.findaspot.backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Payment Repository - yahan par payments table ke liye database operations hain
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // yahan par booking ke payment find karne ka method hai
    Optional<Payment> findByBooking(Booking booking);

    // yahan par status ke basis par payments find karne ka method hai
    List<Payment> findByStatus(String status);

    // yahan par payment method ke basis par payments find karne ka method hai
    List<Payment> findByMethod(String method);

    // yahan par transaction ID se payment find karne ka method hai
    Optional<Payment> findByTransactionId(String transactionId);
}