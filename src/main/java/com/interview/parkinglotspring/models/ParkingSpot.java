package com.interview.parkinglotspring.models;

import com.interview.parkinglotspring.models.enums.ParkingSpotStatus;
import com.interview.parkinglotspring.models.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParkingSpot extends BaseClass{
    private ParkingSpotStatus status;
    private VehicleType supportedVehicles;
    private Long floorId;

    public ParkingSpot(VehicleType supportedVehicles, Long floorId) {
        this.supportedVehicles = supportedVehicles;
        this.floorId = floorId;
        this.status = ParkingSpotStatus.EMPTY;
    }
}
