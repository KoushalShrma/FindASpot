package com.findaspot.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Parking Lot Entity - yahan par parking_lots table ka representation hai
 */
@Entity
@Table(name = "parking_lots")
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100)
    @Column(name = "Name", nullable = false)
    private String name;

    @NotBlank(message = "Address is required")
    @Size(max = 255)
    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name = "coordinates")
    private String coordinates;

    @NotNull(message = "Capacity is required")
    @Column(name = "Capacity", nullable = false)
    private Integer capacity;

    @NotNull(message = "Price per hour is required")
    @Column(name = "Price_Per_Hour", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerHour;

    @Size(max = 50)
    @Column(name = "Type")
    private String type;

    @Size(max = 20)
    @Column(name = "Contact_number")
    private String contactNumber;

    @NotBlank(message = "Parking ID is required")
    @Size(max = 50)
    @Column(name = "parking_id", nullable = false, unique = true)
    private String parkingId;

    @NotBlank(message = "Password is required")
    @Column(name = "parking_password", nullable = false)
    private String parkingPassword;

    // yahan par default constructor hai
    public ParkingLot() {}

    // yahan par constructor with parameters hai
    public ParkingLot(String name, String address, Integer capacity, BigDecimal pricePerHour, String parkingId, String parkingPassword) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.pricePerHour = pricePerHour;
        this.parkingId = parkingId;
        this.parkingPassword = parkingPassword;
    }

    // yahan par getters aur setters hain
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getParkingId() {
        return parkingId;
    }

    public void setParkingId(String parkingId) {
        this.parkingId = parkingId;
    }

    public String getParkingPassword() {
        return parkingPassword;
    }

    public void setParkingPassword(String parkingPassword) {
        this.parkingPassword = parkingPassword;
    }
}