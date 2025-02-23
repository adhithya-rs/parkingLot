package com.interview.parkinglotspring.models;

import com.interview.parkinglotspring.models.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vehicle extends BaseClass{
    private String licensePlate;
    private String ownerName;
    private VehicleType vehicleType;
    private boolean isParked;

    public Vehicle(String licensePlate, String ownerName, VehicleType vehicleType) {
        this.licensePlate = licensePlate;
        this.ownerName = ownerName;
        this.vehicleType = vehicleType;
        this.isParked = true;
    }
}
