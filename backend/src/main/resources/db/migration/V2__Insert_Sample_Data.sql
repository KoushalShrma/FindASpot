-- Sample Data Migration - yahan par testing ke liye sample data hai
-- Demo parking lots aur real-time parking info

-- Insert sample parking lots - yahan par demo parking lots insert kar rahe hain
INSERT INTO parking_lots (Name, Address, coordinates, Capacity, Price_Per_Hour, Type, Contact_number, parking_id, parking_password) VALUES
('City Center Parking', 'Downtown Main Street, Mumbai', '19.0760,72.8777', 50, 15.00, 'premium', '+91-9876543210', 'CITY001', '$2a$10$rQ8K9XzLQg1fV2h.bXgK6eJ5Q3zN7mP8wR4tY6sA9bC0dE1fG2hI3'),
('Mall Parking Plaza', 'Shopping Mall Complex, Delhi', '28.6139,77.2090', 100, 10.00, 'standard', '+91-9876543211', 'MALL001', '$2a$10$rQ8K9XzLQg1fV2h.bXgK6eJ5Q3zN7mP8wR4tY6sA9bC0dE1fG2hI4'),
('Airport Parking Hub', 'International Airport, Bangalore', '12.9716,77.5946', 200, 20.00, 'premium', '+91-9876543212', 'ARPT001', '$2a$10$rQ8K9XzLQg1fV2h.bXgK6eJ5Q3zN7mP8wR4tY6sA9bC0dE1fG2hI5'),
('Hospital Parking', 'City Hospital Campus, Chennai', '13.0827,80.2707', 75, 8.00, 'standard', '+91-9876543213', 'HOSP001', '$2a$10$rQ8K9XzLQg1fV2h.bXgK6eJ5Q3zN7mP8wR4tY6sA9bC0dE1fG2hI6'),
('Tech Park Parking', 'IT Tech Park, Hyderabad', '17.3850,78.4867', 300, 12.00, 'corporate', '+91-9876543214', 'TECH001', '$2a$10$rQ8K9XzLQg1fV2h.bXgK6eJ5Q3zN7mP8wR4tY6sA9bC0dE1fG2hI7');

-- Insert real-time parking info for City Center Parking (ID: 1) - yahan par first parking lot ke liye slots create kar rahe hain
INSERT INTO real_time_parking_info (parking_id, slot_no, is_occupied, vehicle_no, entry_time) VALUES
-- Premium slots (1-10) - some occupied
(1, 1, true, 'MH01AB1234', NOW() - INTERVAL 2 HOUR),
(1, 2, false, null, null),
(1, 3, true, 'MH01CD5678', NOW() - INTERVAL 1 HOUR),
(1, 4, false, null, null),
(1, 5, false, null, null),
(1, 6, true, 'MH01EF9012', NOW() - INTERVAL 30 MINUTE),
(1, 7, false, null, null),
(1, 8, false, null, null),
(1, 9, true, 'MH01GH3456', NOW() - INTERVAL 3 HOUR),
(1, 10, false, null, null),
-- Standard slots (11-50) - mostly available
(1, 11, false, null, null),
(1, 12, false, null, null),
(1, 13, true, 'MH01IJ7890', NOW() - INTERVAL 45 MINUTE),
(1, 14, false, null, null),
(1, 15, false, null, null);

-- Add remaining empty slots for City Center Parking
INSERT INTO real_time_parking_info (parking_id, slot_no, is_occupied)
SELECT 1, slot_number, false
FROM (
    SELECT generate_series(16, 50) AS slot_number
) AS slots;

-- Insert real-time parking info for Mall Parking Plaza (ID: 2) - yahan par second parking lot ke liye slots create kar rahe hain
INSERT INTO real_time_parking_info (parking_id, slot_no, is_occupied)
SELECT 2, slot_number, false
FROM (
    SELECT generate_series(1, 100) AS slot_number
) AS slots;

-- Add some occupied slots for Mall Parking
UPDATE real_time_parking_info 
SET is_occupied = true, vehicle_no = CONCAT('DL', LPAD(slot_no::text, 2, '0'), 'XY', LPAD((1000 + slot_no)::text, 4, '0')), entry_time = NOW() - INTERVAL (slot_no % 4) HOUR
WHERE parking_id = 2 AND slot_no IN (5, 12, 18, 25, 33, 41, 52, 67, 78, 89);

-- Insert sample admin user - yahan par demo admin user create kar rahe hain
INSERT INTO users (role, name, email, password, otp, is_verified) VALUES
('admin', 'System Administrator', 'admin@findaspot.com', '$2a$10$rQ8K9XzLQg1fV2h.bXgK6eJ5Q3zN7mP8wR4tY6sA9bC0dE1fG2hI3', null, true);

-- Insert sample customer user - yahan par demo customer user create kar rahe hain
INSERT INTO users (role, name, email, password, otp, is_verified) VALUES
('customer', 'Demo Customer', 'customer@findaspot.com', '$2a$10$rQ8K9XzLQg1fV2h.bXgK6eJ5Q3zN7mP8wR4tY6sA9bC0dE1fG2hI3', null, true);

-- Insert customer profile for demo customer
INSERT INTO customer_profiles (user_id, name, phone_no, vehicle_no, age, gender, address, city, state) VALUES
((SELECT user_id FROM users WHERE email = 'customer@findaspot.com'), 'Demo Customer', '+91-9876543210', 'MH01AB1234', 30, 'male', '123 Demo Street', 'Mumbai', 'Maharashtra');