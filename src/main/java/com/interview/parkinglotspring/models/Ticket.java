package com.interview.parkinglotspring.models;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class Ticket extends BaseClass{
    private ParkingSpot parkingSpot;
    private Vehicle vehicle;
    private Date entryTime;
    private Gate gate;

    public Ticket(ParkingSpot parkingSpot, Vehicle vehicle, Gate gate) {
        this.parkingSpot = parkingSpot;
        this.vehicle = vehicle;
        this.entryTime = new Date();
        this.gate = gate;
    }

    public void printTicket(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Define human-readable format
        String formattedEntryTime = sdf.format(entryTime);  // Convert date to formatted string

        System.out.println("Ticket ID: "+this.getId()
                +"\nTicket Issuer Name: "+gate.getOperator().getName()
                +"\nTicket Issuer Gate: "+gate.getId()
                +"\nParking Location: Floor-"+parkingSpot.getFloorId()+" "+parkingSpot.getId()
                +"\nVehicle Number: "+vehicle.getLicensePlate()
                +"\nOwner Name: "+vehicle.getOwnerName()
                +"\nEntry Time: "+formattedEntryTime);
        System.out.println();
    }
}
