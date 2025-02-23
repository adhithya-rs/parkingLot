package com.interview.parkinglotspring.models;

import com.interview.parkinglotspring.models.enums.FloorStatus;
import com.interview.parkinglotspring.models.enums.FloorType;
import com.interview.parkinglotspring.models.enums.VehicleType;
import com.interview.parkinglotspring.models.enums.VehicleTypeParkingAvailabilityStatus;
import com.interview.parkinglotspring.repositories.ParkingSpotRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Floor extends BaseClass{
    private static ParkingSpotRepository parkingSpotRepository;

    static{
        parkingSpotRepository = new ParkingSpotRepository();
    }


    private Map<VehicleType, List<ParkingSpot>> parkingSpotsMap;
    private Map<VehicleType, VehicleTypeParkingAvailabilityStatus> vehicleTypeParkingAvailabilityStatus;
    private FloorStatus floorStatus;
    private FloorType floorType;
    private Map<VehicleType, Long> vehicleTypeMaxLimit;
    private Map<VehicleType, Long> vehicleTypeOccupied;

    private Floor() {

    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder{
        private Map<VehicleType, List<ParkingSpot>> parkingSpotsMap;
        private Map<VehicleType, VehicleTypeParkingAvailabilityStatus> vehicleTypeParkingAvailabilityStatus;
        private FloorStatus floorStatus;
        private Map<VehicleType, Long> vehicleTypeMaxLimit;
        private Map<VehicleType, Long> vehicleTypeOccupied;
        private FloorType floorType;

        public Builder setParkingSpotsMap(Long floorId, Long cars, Long bikes) {
            parkingSpotsMap = new HashMap<>();
            List<ParkingSpot> carParkingSpots = new ArrayList<>();
            int i=1;
            for(; i<=cars; i++){
                carParkingSpots.add(parkingSpotRepository.save( new ParkingSpot(VehicleType.CAR, floorId)));
            }
            parkingSpotsMap.put(VehicleType.CAR, carParkingSpots);

            List<ParkingSpot> bikeParkingSpots = new ArrayList<>();
            for(; i<=bikes+cars; i++){
                bikeParkingSpots.add(parkingSpotRepository.save( new ParkingSpot(VehicleType.BIKE, floorId)));
            }
            parkingSpotsMap.put(VehicleType.BIKE, bikeParkingSpots);
            return this;
        }

        public Builder setVehicleTypeParkingAvailabilityStatus() {
            vehicleTypeParkingAvailabilityStatus = new HashMap<>();
            vehicleTypeParkingAvailabilityStatus.put(VehicleType.CAR, VehicleTypeParkingAvailabilityStatus.AVAILABLE);
            vehicleTypeParkingAvailabilityStatus.put(VehicleType.BIKE, VehicleTypeParkingAvailabilityStatus.AVAILABLE);
            return this;
        }

        public Builder setFloorStatus(FloorStatus floorStatus) {
            this.floorStatus = floorStatus;
            return this;
        }

        public Builder setVehicleTypeMaxLimit(Long cars, Long bikes) {
            vehicleTypeMaxLimit = new HashMap<>();
            vehicleTypeMaxLimit.put(VehicleType.CAR, cars);
            vehicleTypeMaxLimit.put(VehicleType.BIKE, bikes);
            return this;
        }

        public Builder setVehicleTypeOccupied() {
            vehicleTypeOccupied = new HashMap<>();
            vehicleTypeOccupied.put(VehicleType.CAR, 0L);
            vehicleTypeOccupied.put(VehicleType.BIKE, 0L);
            return this;
        }

        public Builder setFloorType(FloorType floorType) {
            this.floorType = floorType;
            return this;
        }

        public Floor build() {
            Floor floor = new Floor();
            floor.setFloorStatus(floorStatus);
            floor.setVehicleTypeParkingAvailabilityStatus(vehicleTypeParkingAvailabilityStatus);
            floor.setVehicleTypeMaxLimit(vehicleTypeMaxLimit);
            floor.setVehicleTypeOccupied(vehicleTypeOccupied);
            floor.setParkingSpotsMap(parkingSpotsMap);
            floor.setFloorType(floorType);
            return floor;
        }


    }



}
