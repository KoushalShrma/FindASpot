-- FindASpot Database Schema Migration
-- yahan par database tables create kar rahe hain

-- Users table - yahan par user information store hoti hai
CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(20) NOT NULL DEFAULT 'customer',
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    otp VARCHAR(10),
    is_verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Customer profiles table - yahan par customer ki detailed information hai
CREATE TABLE IF NOT EXISTS customer_profiles (
    user_id BIGINT PRIMARY KEY,
    name VARCHAR(100),
    phone_no VARCHAR(15),
    vehicle_no VARCHAR(20),
    age INT,
    gender VARCHAR(10),
    address VARCHAR(255),
    city VARCHAR(50),
    state VARCHAR(50),
    photo VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Parking lots table - yahan par parking lots ki information hai
CREATE TABLE IF NOT EXISTS parking_lots (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Address VARCHAR(255) NOT NULL,
    coordinates VARCHAR(255),
    Capacity INT NOT NULL,
    Price_Per_Hour DECIMAL(10,2) NOT NULL,
    Type VARCHAR(50),
    Contact_number VARCHAR(20),
    parking_id VARCHAR(50) NOT NULL UNIQUE,
    parking_password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Bookings table - yahan par parking bookings ki information hai
CREATE TABLE IF NOT EXISTS bookings (
    booking_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    parking_id BIGINT NOT NULL,
    slot_no INT NOT NULL,
    entry_time TIMESTAMP,
    exit_time TIMESTAMP,
    status VARCHAR(20) DEFAULT 'active',
    amount DECIMAL(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (parking_id) REFERENCES parking_lots(ID) ON DELETE CASCADE
);

-- Payments table - yahan par payment information hai
CREATE TABLE IF NOT EXISTS payments (
    payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL UNIQUE,
    amount DECIMAL(10,2) NOT NULL,
    method VARCHAR(20),
    status VARCHAR(20) DEFAULT 'pending',
    paid_at TIMESTAMP,
    transaction_id VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE
);

-- Real time parking info table - yahan par live slot information hai
CREATE TABLE IF NOT EXISTS real_time_parking_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parking_id BIGINT NOT NULL,
    slot_no INT NOT NULL,
    is_occupied BOOLEAN DEFAULT FALSE,
    vehicle_no VARCHAR(20),
    entry_time TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parking_id) REFERENCES parking_lots(ID) ON DELETE CASCADE,
    UNIQUE KEY unique_parking_slot (parking_id, slot_no)
);

-- Indexes for better performance - yahan par database performance ke liye indexes hain
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_bookings_user_id ON bookings(user_id);
CREATE INDEX idx_bookings_parking_id ON bookings(parking_id);
CREATE INDEX idx_bookings_status ON bookings(status);
CREATE INDEX idx_payments_status ON payments(status);
CREATE INDEX idx_real_time_parking_id ON real_time_parking_info(parking_id);
CREATE INDEX idx_real_time_occupied ON real_time_parking_info(is_occupied);