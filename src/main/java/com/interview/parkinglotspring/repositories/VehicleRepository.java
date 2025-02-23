package com.interview.parkinglotspring.repositories;


import com.interview.parkinglotspring.models.Floor;
import com.interview.parkinglotspring.models.Vehicle;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@Repository
public class VehicleRepository {
    private static Map<String, Vehicle> vehicleMap = new HashMap<>();
    private Long vehicleId = 0L;

    public Optional<Vehicle> findByVehicleNumber(String vehicleNumber) {
        return Optional.ofNullable(vehicleMap.get(vehicleNumber));
    }

    public Vehicle save(Vehicle vehicle) {
        if(vehicleMap.get(vehicle.getLicensePlate()) == null) {
            vehicleId +=1;
            vehicle.setId(vehicleId);
            vehicleMap.put(vehicle.getLicensePlate(), vehicle);
        }else{
            vehicleMap.put(vehicle.getLicensePlate(), vehicle);
        }
        return vehicle;
    }


}
