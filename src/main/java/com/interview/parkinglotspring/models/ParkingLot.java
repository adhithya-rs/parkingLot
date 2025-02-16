package com.interview.parkinglotspring.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParkingLot extends BaseClass{
    private String name;
    private String address;
    private List<Gate> gates;
    private LotStatus status;
    private List<Floor> floors;
    private List<VehicleType> supportedVehicles;

}
