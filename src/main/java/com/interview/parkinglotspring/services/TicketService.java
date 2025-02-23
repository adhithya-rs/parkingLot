package com.interview.parkinglotspring.services;

import com.interview.parkinglotspring.dtos.GenerateTicketRequestDto;
import com.interview.parkinglotspring.dtos.GenerateTicketResponseDto;
import com.interview.parkinglotspring.models.enums.ResponseStatus;
import com.interview.parkinglotspring.models.Gate;
import com.interview.parkinglotspring.models.ParkingSpot;
import com.interview.parkinglotspring.models.Ticket;
import com.interview.parkinglotspring.models.Vehicle;
import com.interview.parkinglotspring.repositories.GateRepository;
import com.interview.parkinglotspring.repositories.TicketRepository;
import com.interview.parkinglotspring.repositories.VehicleRepository;
import com.interview.parkinglotspring.strategies.parkingSpotAssignmentStrategy.ParkingSpotAssignmentStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private GateRepository gateRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private TicketRepository ticketRepository;

    public GenerateTicketResponseDto generateTicket(GenerateTicketRequestDto generateTicketRequestDto) {
        String vehicleNumber = generateTicketRequestDto.getVehicleNumber();
        Optional<Vehicle> vehicleCheck =vehicleRepository.findByVehicleNumber(vehicleNumber);
        Vehicle vehicle = null;
        if(vehicleCheck.isPresent() && vehicleCheck.get().isParked()) {
            return new GenerateTicketResponseDto(null, "The Vehicle with number: "+vehicleNumber+", is already parked inside", ResponseStatus.FAILURE);
        }else if(vehicleCheck.isPresent()){
            vehicle = vehicleCheck.get();
        }

        if(vehicleCheck.isEmpty()){
            vehicle = vehicleRepository.save(new Vehicle(generateTicketRequestDto.getVehicleNumber()
                    , generateTicketRequestDto.getOwnerName()
                    , generateTicketRequestDto.getVehicleType()));
        }

        Optional<Gate> gate = gateRepository.findById(generateTicketRequestDto.getGateId());
        ParkingSpotAssignmentStrategy parkingSpotAssignmentStrategy = generateTicketRequestDto.getParkingSpotAssignmentStrategy();
        ParkingSpot parkingSpot = parkingSpotAssignmentStrategy.assignParkingSpot(vehicle);
        if(parkingSpot == null){
            System.out.println("Debug: Parking Spot not found");
        }
        return new GenerateTicketResponseDto(ticketRepository.save(new Ticket(parkingSpot, vehicle, gate.get())), "Please view the ticket details:", ResponseStatus.SUCCESS);
    }


}
