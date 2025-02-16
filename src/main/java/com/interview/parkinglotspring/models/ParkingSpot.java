package com.interview.parkinglotspring.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParkingSpot extends BaseClass{
    private ParkingSpotStatus status;
    private List<VehicleType> supportedVehicles;
}
