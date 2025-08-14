package com.findaspot.backend.repository;

import com.findaspot.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User Repository - yahan par user table ke liye database operations hain
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // yahan par email se user find karne ka method hai
    Optional<User> findByEmail(String email);

    // yahan par email exist karne ka check hai
    boolean existsByEmail(String email);

    // yahan par role ke basis par users find karne ka method hai
    Iterable<User> findByRole(String role);

    // yahan par verified users find karne ka method hai
    Iterable<User> findByIsVerified(boolean isVerified);
}