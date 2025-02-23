package com.interview.parkinglotspring.dtos;

import com.interview.parkinglotspring.models.enums.VehicleType;
import com.interview.parkinglotspring.strategies.parkingSpotAssignmentStrategy.ParkingSpotAssignmentStrategy;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GenerateTicketRequestDto {
    private String vehicleNumber;
    private Long gateId;
    private VehicleType vehicleType;
    private String ownerName;
    private ParkingSpotAssignmentStrategy parkingSpotAssignmentStrategy;

}
