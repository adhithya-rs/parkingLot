package com.interview.parkinglotspring.repositories;

import com.interview.parkinglotspring.models.Floor;
import com.interview.parkinglotspring.models.ParkingLot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@Component
public class ParkingLotRepository {
    public static Map<Long, ParkingLot> parkingLotMap = new HashMap<>();
    private Long parkingLotId = 0L;

    public ParkingLot findById(long id) {
        return parkingLotMap.get(id);
    }

    public ParkingLot save(ParkingLot parkingLot) {
        if(parkingLotMap.get(parkingLot.getId()) == null) {
            parkingLotId +=1;
            parkingLot.setId(parkingLotId);
            parkingLotMap.put(parkingLot.getId(), parkingLot);
        }else{
            parkingLotMap.put(parkingLot.getId(), parkingLot);
        }
        return parkingLot;
    }
}
