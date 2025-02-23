package com.interview.parkinglotspring.factories;

import com.interview.parkinglotspring.models.enums.ParkingSpotAssignmentStrategyType;
import com.interview.parkinglotspring.strategies.parkingSpotAssignmentStrategy.*;

public class ParkingSpotAssignmentStrategyFactory {

    public static ParkingSpotAssignmentStrategy getParkingSpotAssignmentStrategy(ParkingSpotAssignmentStrategyType type) {

        switch (type) {
            case VIP:
                return new VIPParkingAssignmentStrategy();
            case CHEAPEST:
                return new CheapestParkingSpotAssignmentStrategy();
            case RANDOM:
                return new RandomParkingSpotAssignmentStrategy();
            case NEAREST:
                return new NearestParkingSpotAssignmentStrategy();
                default:
                    return null;
        }
    }
}
