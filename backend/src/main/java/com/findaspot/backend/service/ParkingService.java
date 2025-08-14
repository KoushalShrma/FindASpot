package com.findaspot.backend.service;

import com.findaspot.backend.dto.ParkingSlotDto;
import com.findaspot.backend.entity.ParkingLot;
import com.findaspot.backend.entity.RealTimeParkingInfo;
import com.findaspot.backend.repository.ParkingLotRepository;
import com.findaspot.backend.repository.RealTimeParkingInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Parking Service - yahan par parking related operations hain
 */
@Service
@Transactional
public class ParkingService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private RealTimeParkingInfoRepository realTimeParkingInfoRepository;

    // yahan par parking lots ki list get karne ka method hai
    public List<ParkingLot> getAllParkingLots() {
        return parkingLotRepository.findAll();
    }

    // yahan par parking lot ID se parking lot get karne ka method hai
    public Optional<ParkingLot> getParkingLotById(Long id) {
        return parkingLotRepository.findById(id);
    }

    // yahan par parking ID se parking lot get karne ka method hai
    public Optional<ParkingLot> getParkingLotByParkingId(String parkingId) {
        return parkingLotRepository.findByParkingId(parkingId);
    }

    // yahan par parking lot ke real-time slots get karne ka method hai
    public List<ParkingSlotDto> getRealTimeParkingInfo(Long parkingLotId) {
        Optional<ParkingLot> parkingLotOpt = parkingLotRepository.findById(parkingLotId);
        if (parkingLotOpt.isEmpty()) {
            throw new RuntimeException("Parking lot not found");
        }

        ParkingLot parkingLot = parkingLotOpt.get();
        List<RealTimeParkingInfo> slots = realTimeParkingInfoRepository.findByParkingLotOrderBySlotNo(parkingLot);

        // yahan par entity ko DTO mein convert kar rahe hain
        return slots.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // yahan par specific slot ka status get karne ka method hai
    public Optional<ParkingSlotDto> getSlotStatus(Long parkingLotId, Integer slotNo) {
        Optional<ParkingLot> parkingLotOpt = parkingLotRepository.findById(parkingLotId);
        if (parkingLotOpt.isEmpty()) {
            return Optional.empty();
        }

        ParkingLot parkingLot = parkingLotOpt.get();
        Optional<RealTimeParkingInfo> slotOpt = realTimeParkingInfoRepository
                .findByParkingLotAndSlotNo(parkingLot, slotNo);

        return slotOpt.map(this::convertToDto);
    }

    // yahan par slot occupy karne ka method hai
    public boolean occupySlot(Long parkingLotId, Integer slotNo, String vehicleNo) {
        Optional<ParkingLot> parkingLotOpt = parkingLotRepository.findById(parkingLotId);
        if (parkingLotOpt.isEmpty()) {
            return false;
        }

        ParkingLot parkingLot = parkingLotOpt.get();
        Optional<RealTimeParkingInfo> slotOpt = realTimeParkingInfoRepository
                .findByParkingLotAndSlotNo(parkingLot, slotNo);

        if (slotOpt.isPresent()) {
            RealTimeParkingInfo slot = slotOpt.get();
            if (!slot.getIsOccupied()) {
                slot.occupySlot(vehicleNo);
                realTimeParkingInfoRepository.save(slot);
                return true;
            }
        }
        return false;
    }

    // yahan par slot free karne ka method hai
    public boolean freeSlot(Long parkingLotId, Integer slotNo) {
        Optional<ParkingLot> parkingLotOpt = parkingLotRepository.findById(parkingLotId);
        if (parkingLotOpt.isEmpty()) {
            return false;
        }

        ParkingLot parkingLot = parkingLotOpt.get();
        Optional<RealTimeParkingInfo> slotOpt = realTimeParkingInfoRepository
                .findByParkingLotAndSlotNo(parkingLot, slotNo);

        if (slotOpt.isPresent()) {
            RealTimeParkingInfo slot = slotOpt.get();
            if (slot.getIsOccupied()) {
                slot.freeSlot();
                realTimeParkingInfoRepository.save(slot);
                return true;
            }
        }
        return false;
    }

    // yahan par available slots count karne ka method hai
    public long getAvailableSlotCount(Long parkingLotId) {
        Optional<ParkingLot> parkingLotOpt = parkingLotRepository.findById(parkingLotId);
        if (parkingLotOpt.isEmpty()) {
            return 0;
        }

        ParkingLot parkingLot = parkingLotOpt.get();
        List<RealTimeParkingInfo> availableSlots = realTimeParkingInfoRepository
                .findByParkingLotAndIsOccupied(parkingLot, false);
        
        return availableSlots.size();
    }

    // yahan par naya parking lot create karne ka method hai (admin use)
    public ParkingLot createParkingLot(ParkingLot parkingLot) {
        ParkingLot savedParkingLot = parkingLotRepository.save(parkingLot);
        
        // yahan par parking lot ke liye real-time parking info slots create kar rahe hain
        for (int i = 1; i <= parkingLot.getCapacity(); i++) {
            RealTimeParkingInfo slot = new RealTimeParkingInfo(savedParkingLot, i);
            realTimeParkingInfoRepository.save(slot);
        }
        
        return savedParkingLot;
    }

    // yahan par entity ko DTO mein convert karne ka helper method hai
    private ParkingSlotDto convertToDto(RealTimeParkingInfo slot) {
        ParkingSlotDto dto = new ParkingSlotDto();
        dto.setSlotNo(slot.getSlotNo());
        dto.setIsOccupied(slot.getIsOccupied());
        dto.setVehicleNo(slot.getVehicleNo());
        dto.setEntryTime(slot.getEntryTime());
        
        // yahan par slot type determine kar rahe hain (simplified logic)
        if (slot.getSlotNo() <= 10) {
            dto.setSlotType("premium");
        } else if (slot.getSlotNo() <= 20) {
            dto.setSlotType("standard");
        } else {
            dto.setSlotType("standard");
        }
        
        return dto;
    }
}