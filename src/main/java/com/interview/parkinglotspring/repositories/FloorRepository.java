package com.interview.parkinglotspring.repositories;

import com.interview.parkinglotspring.models.Floor;
import com.interview.parkinglotspring.models.Ticket;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@Repository
public class FloorRepository {
    public static Map<Long, Floor> floorMap = new HashMap<>();
    private Long floorId = 0L;

    public Optional<Floor> findById(long id) {
        return Optional.ofNullable(floorMap.get(id));
    }

    public Floor save(Floor floor) {
        if(floorMap.get(floor.getId()) == null) {
            floorId +=1;
            floor.setId(floorId);
            floorMap.put(floor.getId(), floor);
        }else{
            floorMap.put(floor.getId(), floor);
        }
        return floor;
    }
}
