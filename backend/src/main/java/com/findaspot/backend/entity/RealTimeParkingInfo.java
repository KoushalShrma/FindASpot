package com.findaspot.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Real Time Parking Info Entity - yahan par real_time_parking_info table ka representation hai
 */
@Entity
@Table(name = "real_time_parking_info")
public class RealTimeParkingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Parking lot is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_id", nullable = false)
    private ParkingLot parkingLot;

    @NotNull(message = "Slot number is required")
    @Column(name = "slot_no", nullable = false)
    private Integer slotNo;

    @Column(name = "is_occupied", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isOccupied = false;

    @Column(name = "vehicle_no")
    private String vehicleNo;

    @Column(name = "entry_time")
    private LocalDateTime entryTime;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // yahan par default constructor hai
    public RealTimeParkingInfo() {
        this.updatedAt = LocalDateTime.now();
    }

    // yahan par constructor with parameters hai
    public RealTimeParkingInfo(ParkingLot parkingLot, Integer slotNo) {
        this();
        this.parkingLot = parkingLot;
        this.slotNo = slotNo;
        this.isOccupied = false;
    }

    // yahan par update method hai slot occupy karne ke liye
    public void occupySlot(String vehicleNo) {
        this.isOccupied = true;
        this.vehicleNo = vehicleNo;
        this.entryTime = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // yahan par update method hai slot free karne ke liye
    public void freeSlot() {
        this.isOccupied = false;
        this.vehicleNo = null;
        this.entryTime = null;
        this.updatedAt = LocalDateTime.now();
    }

    // yahan par getters aur setters hain
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public Integer getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(Integer slotNo) {
        this.slotNo = slotNo;
    }

    public Boolean getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(Boolean isOccupied) {
        this.isOccupied = isOccupied;
        this.updatedAt = LocalDateTime.now();
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}