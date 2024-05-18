package com.smartparkinglot.backend.service;

import com.smartparkinglot.backend.entity.ParkingLot;
import com.smartparkinglot.backend.repository.ParkingLotRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;

    @Autowired
    public ParkingLotService(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }
    public List<ParkingLot> getParkingLotsWithinRadius(BigDecimal latitude, BigDecimal longitude, Long radius) {
        return parkingLotRepository.findWithinRadius(latitude, longitude, radius);
    }

    public ParkingLot getParkingLotById(Long id){
        if(parkingLotRepository.findById(id).isPresent())
            return parkingLotRepository.findById(id).get();
        else return null;
    }

    public List<ParkingLot> getAllParkingLots() {
        return parkingLotRepository.findAll();
    }

    public void addNewParkingLot(ParkingLot parkingLot) {
        if (parkingLotRepository.existsById(parkingLot.getId())) {
            throw new IllegalStateException("Parking lot with ID " + parkingLot.getId() + " already exists");
        }
        parkingLotRepository.save(parkingLot);
    }

    // Transactional <=> no need of verification queries
    @Transactional
    public void updateParkingLot(Long parkingLotId, int nrSpots, BigDecimal latitude, BigDecimal longitude){
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new IllegalStateException("Parking lot id " + parkingLotId + " doesn't exist in parking_lots"));
        if( nrSpots != parkingLot.getNrSpots()){
            parkingLot.setNrSpots(nrSpots);
        }
        if(latitude != null && !latitude.equals(parkingLot.getLatitude())){
            parkingLot.setLatitude(latitude);
        }
        if(longitude != null && !longitude.equals(parkingLot.getLongitude())){
            parkingLot.setLongitude(longitude);
        }
    }

    @Transactional
    public void deleteParkingLot(ParkingLot parkingLot){
        parkingLotRepository.deleteParkingLot(parkingLot.getId());
    }

}
