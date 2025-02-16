package com.interview.parkinglotspring.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Ticket extends BaseClass{
    private ParkingSpot parkingSpot;
    private Vehicle vehicle;
    private Operator operator;
    private Date entryTime;
    private Gate gate;
}
