package com.interview.parkinglotspring.strategies.parkingSpotAssignmentStrategy;

import com.interview.parkinglotspring.models.Floor;
import com.interview.parkinglotspring.models.ParkingLot;
import com.interview.parkinglotspring.models.ParkingSpot;
import com.interview.parkinglotspring.models.Vehicle;
import com.interview.parkinglotspring.models.enums.ParkingSpotStatus;
import com.interview.parkinglotspring.repositories.ParkingLotRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NearestParkingSpotAssignmentStrategy implements ParkingSpotAssignmentStrategy {


    @Override
    public ParkingSpot assignParkingSpot(Vehicle vehicle) {
        ParkingLot parkingLot= ParkingLotRepository.parkingLotMap.get(1L);
        List<Floor> floors = parkingLot.getFloors();
        ParkingSpot finalParkingSpot=null;
        for(Floor floor : floors){
            List<ParkingSpot> parkingSpots = floor.getParkingSpotsMap().get(vehicle.getVehicleType());
            for(ParkingSpot parkingSpot : parkingSpots){
                if(parkingSpot.getStatus().equals(ParkingSpotStatus.EMPTY)){
                    lock.lock();
                    if(parkingSpot.getStatus().equals(ParkingSpotStatus.EMPTY)){
                        finalParkingSpot=parkingSpot;
                        finalParkingSpot.setStatus(ParkingSpotStatus.FILLED);
                    }
                    lock.unlock();
                }
            }
        }
        return finalParkingSpot;
    }
}
