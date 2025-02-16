package com.interview.parkinglotspring.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vehicle extends BaseClass{
    private String licensePlate;
    private String ownerName;
    private VehicleType vehicleType;
}
