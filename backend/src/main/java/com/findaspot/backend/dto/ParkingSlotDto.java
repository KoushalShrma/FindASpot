package com.findaspot.backend.dto;

import java.time.LocalDateTime;

/**
 * Parking Slot DTO - yahan par parking slot information hai
 */
public class ParkingSlotDto {

    private Integer slotNo;
    private Boolean isOccupied;
    private String vehicleNo;
    private LocalDateTime entryTime;
    private String slotType; // standard, premium, disabled, ev, large

    // yahan par default constructor hai
    public ParkingSlotDto() {}

    // yahan par constructor with parameters hai
    public ParkingSlotDto(Integer slotNo, Boolean isOccupied, String vehicleNo, LocalDateTime entryTime) {
        this.slotNo = slotNo;
        this.isOccupied = isOccupied;
        this.vehicleNo = vehicleNo;
        this.entryTime = entryTime;
        this.slotType = "standard";
    }

    // yahan par getters aur setters hain
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

    public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }
}