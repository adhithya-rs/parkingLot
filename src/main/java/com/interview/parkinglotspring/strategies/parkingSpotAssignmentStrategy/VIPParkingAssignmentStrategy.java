package com.interview.parkinglotspring.strategies.parkingSpotAssignmentStrategy;

import com.interview.parkinglotspring.models.Floor;
import com.interview.parkinglotspring.models.ParkingLot;
import com.interview.parkinglotspring.models.ParkingSpot;
import com.interview.parkinglotspring.models.Vehicle;
import com.interview.parkinglotspring.models.enums.FloorType;
import com.interview.parkinglotspring.models.enums.ParkingSpotStatus;
import com.interview.parkinglotspring.repositories.ParkingLotRepository;

import java.util.List;


public class VIPParkingAssignmentStrategy implements ParkingSpotAssignmentStrategy{

    @Override
    public ParkingSpot assignParkingSpot(Vehicle vehicle) {
        ParkingLot parkingLot= ParkingLotRepository.parkingLotMap.get(1L);
        List<Floor> floors = parkingLot.getFloors();
        for(Floor floor : floors){
            if(floor.getFloorType().equals(FloorType.VIP)){
                List<ParkingSpot> parkingSpots = floor.getParkingSpotsMap().get(vehicle.getVehicleType());
                for(ParkingSpot parkingSpot : parkingSpots){
                    if(parkingSpot.getStatus().equals(ParkingSpotStatus.EMPTY)){
                        return parkingSpot;
                    }
                }
            }
        }
        return null;
    }
}
