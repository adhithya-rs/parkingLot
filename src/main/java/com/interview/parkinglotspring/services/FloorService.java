package com.interview.parkinglotspring.services;

import com.interview.parkinglotspring.models.*;
import com.interview.parkinglotspring.models.enums.ParkingSpotStatus;
import com.interview.parkinglotspring.models.enums.VehicleType;
import com.interview.parkinglotspring.models.enums.VehicleTypeParkingAvailabilityStatus;
import com.interview.parkinglotspring.observers.OnVehicleEntrySubscriber;
import com.interview.parkinglotspring.observers.OnVehicleExitSubscriber;
import com.interview.parkinglotspring.repositories.FloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class FloorService implements OnVehicleEntrySubscriber, OnVehicleExitSubscriber {
    private FloorRepository floorRepository;

    @Autowired
    public FloorService(FloorRepository floorRepository) {
        this.floorRepository = floorRepository;
    }

    @Override
    public void onVehicleEntry(Ticket ticket) {
        Optional<Floor> optionalFloor = floorRepository.findById(ticket.getParkingSpot().getFloorId());
        Floor floor = null;
        if (optionalFloor.isPresent()) {
            floor = optionalFloor.get();
        }else{
            return;
        }

        VehicleType vehicleType = ticket.getVehicle().getVehicleType();
        ParkingSpot parkingSpot = ticket.getParkingSpot();
        parkingSpot.setStatus(ParkingSpotStatus.FILLED);
        Map<VehicleType, Long> vehicleTypeOccupied = floor.getVehicleTypeOccupied();
        Map<VehicleType, Long> vehicleTypeMaxLimit = floor.getVehicleTypeMaxLimit();
        Map<VehicleType, VehicleTypeParkingAvailabilityStatus> vehicleTypeParkingAvailabilityStatus = floor.getVehicleTypeParkingAvailabilityStatus();
        vehicleTypeOccupied.put(vehicleType, vehicleTypeOccupied.get(vehicleType)+1);
        if(vehicleTypeOccupied.get(vehicleType).equals( vehicleTypeMaxLimit.get(vehicleType))){
            vehicleTypeParkingAvailabilityStatus.put(vehicleType, VehicleTypeParkingAvailabilityStatus.UNAVAILABLE);
        }
    }

    @Override
    public void onVehicleExit(Bill bill) {
        Optional<Floor> optionalFloor = floorRepository.findById(bill.getTicket().getParkingSpot().getFloorId());
        Ticket ticket = bill.getTicket();
        Floor floor = null;
        if (optionalFloor.isPresent()) {
            floor = optionalFloor.get();
        }else{
            return;
        }

        VehicleType vehicleType = ticket.getVehicle().getVehicleType();
        ParkingSpot parkingSpot = ticket.getParkingSpot();
        if(parkingSpot.getStatus() == ParkingSpotStatus.FILLED){
            parkingSpot.setStatus(ParkingSpotStatus.EMPTY);
        }
        Map<VehicleType, Long> vehicleTypeOccupied = floor.getVehicleTypeOccupied();
        vehicleTypeOccupied.put(vehicleType, vehicleTypeOccupied.get(vehicleType)-1);
        Map<VehicleType, VehicleTypeParkingAvailabilityStatus> vehicleTypeParkingAvailabilityStatus = floor.getVehicleTypeParkingAvailabilityStatus();
        if(vehicleTypeParkingAvailabilityStatus.get(vehicleType).equals(VehicleTypeParkingAvailabilityStatus.UNAVAILABLE)){
            vehicleTypeParkingAvailabilityStatus.put(vehicleType, VehicleTypeParkingAvailabilityStatus.AVAILABLE);
        }
    }
}
