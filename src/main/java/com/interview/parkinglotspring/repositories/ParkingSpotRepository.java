package com.interview.parkinglotspring.repositories;

import com.interview.parkinglotspring.models.ParkingSpot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@Repository
public class ParkingSpotRepository {
    private static Map<Long, ParkingSpot> parkingSpotMap = new HashMap<>();
    private Long parkingSpotId = 0L;

    public Optional<ParkingSpot> findById(long id) {
        return Optional.ofNullable(parkingSpotMap.get(id));
    }

    public ParkingSpot save(ParkingSpot parkingSpot) {
        if(parkingSpotMap.get(parkingSpot.getId()) == null) {
            parkingSpotId +=1;
            parkingSpot.setId(parkingSpotId);
            parkingSpotMap.put(parkingSpot.getId(), parkingSpot);
        }else{
            parkingSpotMap.put(parkingSpot.getId(), parkingSpot);
        }
        return parkingSpot;
    }
}
