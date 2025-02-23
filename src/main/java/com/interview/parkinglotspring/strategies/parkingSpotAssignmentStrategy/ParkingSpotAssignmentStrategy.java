package com.interview.parkinglotspring.strategies.parkingSpotAssignmentStrategy;

import com.interview.parkinglotspring.models.ParkingSpot;
import com.interview.parkinglotspring.models.Vehicle;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public interface ParkingSpotAssignmentStrategy {
    public static Lock lock = new ReentrantLock();
    ParkingSpot assignParkingSpot(Vehicle vehicle);
}
