package com.interview.parkinglotspring.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Floor extends BaseClass{
    private List<ParkingSpot> parkingSpots;
    private FloorStatus status;
}
