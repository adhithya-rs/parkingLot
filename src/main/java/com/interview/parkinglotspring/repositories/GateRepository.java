package com.interview.parkinglotspring.repositories;

import com.interview.parkinglotspring.models.Gate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@Repository
public class GateRepository {
    private static Map<Long, Gate> gateMap = new HashMap<>();
    private Long gateId = 0L;


    public Optional<Gate> findById(long id) {
        return Optional.ofNullable(gateMap.get(id));
    }

    public Gate save(Gate gate) {
        if(gateMap.get(gate.getId()) == null) {
            gateId +=1;
            gate.setId(gateId);
            gateMap.put(gate.getId(), gate);
        }else{
            gateMap.put(gate.getId(), gate);
        }
        return gate;
    }
}
