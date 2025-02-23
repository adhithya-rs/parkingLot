package com.interview.parkinglotspring.models;

import com.interview.parkinglotspring.models.enums.*;
import com.interview.parkinglotspring.repositories.FloorRepository;
import com.interview.parkinglotspring.repositories.GateRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ParkingLot extends BaseClass{
    private static GateRepository gateRepository;
    private static FloorRepository floorRepository;

    static{
        gateRepository = new GateRepository();
        floorRepository = new FloorRepository();
    }

    private String name;
    private String address;
    private List<Gate> gates;
    private LotStatus status;
    private List<Floor> floors;
    private List<VehicleType> supportedVehicles;

    private ParkingLot() {
        this.status = LotStatus.FUNCTIONAL;
        this.supportedVehicles = new ArrayList<>(Arrays.asList(VehicleType.values()));
    }

    public void printParkingLot(){
        System.out.println("\n======= Parking Lot Details =======");
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Status: " + status);

        System.out.println("\nSupported Vehicle Types:");
        for (VehicleType type : supportedVehicles) {
            System.out.println("  - " + type);
        }

        System.out.println("\nGates:");
        if (gates == null || gates.isEmpty()) {
            System.out.println("  No gates available.");
        } else {
            for (Gate gate : gates) {
                System.out.println("  Gate ID: " + gate.getId() + " | Type: " + gate.getGateType() + " | Status: " + gate.getStatus()
                        + " | Operator: " + gate.getOperator().getName());
            }
        }

        System.out.println("\nFloors:");
        if (floors == null || floors.isEmpty()) {
            System.out.println("  No floors available.");
        } else {
            for (Floor floor : floors) {
                System.out.println("\n  Floor ID: " + floor.getId() + " | Type: " + floor.getFloorType() + " | Status: " + floor.getFloorStatus());
                System.out.println("  Vehicle Parking Limits:");
                for (Map.Entry<VehicleType, Long> entry : floor.getVehicleTypeMaxLimit().entrySet()) {
                    Long occupied = floor.getVehicleTypeOccupied().getOrDefault(entry.getKey(), 0L);
                    System.out.println("    - " + entry.getKey() + ": " + occupied + "/" + entry.getValue() + " occupied");
                }

                // Printing Parking Spots for this floor
                System.out.println("  Parking Spots:");
                for (Map.Entry<VehicleType, List<ParkingSpot>> entry : floor.getParkingSpotsMap().entrySet()) {
                    System.out.println("    - " + entry.getKey() + ": ");
                    for (ParkingSpot spot : entry.getValue()) {
                        System.out.println("      Spot ID: " + spot.getId()
                                + " | Status: " + spot.getStatus()
                                + " | Supports: " + spot.getSupportedVehicles());
                    }
                }
            }
        }

        System.out.println("===================================\n");
    }


    public static Builder getBuilder(){
        return new Builder();
    }

    public static class Builder{
        private String name;
        private String address;
        private List<Gate> gates;
        private List<Floor> floors;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setGates(Long entryGates, Long exitGates, List<String> operatorNames) {
            int i=1;
            gates = new ArrayList<>();
            while(i<=entryGates){

                Gate gate = new Gate(operatorNames.get(i-1), GateType.ENTRY);
                gates.add(gateRepository.save(gate));
                i++;
            }
            while(i<=exitGates+entryGates){

                Gate gate = new Gate(operatorNames.get(i-1), GateType.EXIT);
                gates.add(gateRepository.save(gate));
                i++;
            }
            return this;
        }

        public Builder setFloors(Long floors, Long cars, Long bikes) {
            this.floors = new ArrayList<>();
            if(floors==1){
                Floor floor = Floor.getBuilder()
                        .setFloorStatus(FloorStatus.FUNCTIONAL)
                        .setFloorType(FloorType.CHEAPEST)
                        .setVehicleTypeMaxLimit(cars, bikes)
                        .setVehicleTypeOccupied()
                        .setVehicleTypeParkingAvailabilityStatus()
                        .setParkingSpotsMap(1L, cars,bikes)
                        .build();
                this.floors.add(floorRepository.save(floor));
                return this;
            }
            for(long i = 1L; i<=floors/3; i++){
                Floor floor = Floor.getBuilder()
                        .setFloorStatus(FloorStatus.FUNCTIONAL)
                        .setFloorType(FloorType.VIP)
                        .setVehicleTypeMaxLimit(cars, bikes)
                        .setVehicleTypeOccupied()
                        .setVehicleTypeParkingAvailabilityStatus()
                        .setParkingSpotsMap(i, cars,bikes)
                        .build();
                this.floors.add(floorRepository.save(floor));
            }
            for(long i= ((floors/3)+1) ; i<=floors;i++){
                Floor floor = Floor.getBuilder()
                        .setFloorStatus(FloorStatus.FUNCTIONAL)
                        .setFloorType(FloorType.CHEAPEST)
                        .setVehicleTypeMaxLimit(cars, bikes)
                        .setVehicleTypeOccupied()
                        .setVehicleTypeParkingAvailabilityStatus()
                        .setParkingSpotsMap(i, cars,bikes)
                        .build();
                this.floors.add(floorRepository.save(floor));
            }
            return this;
        }


        public ParkingLot build(){
            ParkingLot parkingLot = new ParkingLot();
            parkingLot.setName(name);
            parkingLot.setAddress(address);
            parkingLot.setGates(gates);
            parkingLot.setFloors(floors);
            return parkingLot;
        }
    }

}
