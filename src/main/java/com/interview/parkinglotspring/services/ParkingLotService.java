package com.interview.parkinglotspring.services;

import com.interview.parkinglotspring.models.Bill;
import com.interview.parkinglotspring.models.Ticket;
import com.interview.parkinglotspring.observers.OnVehicleEntrySubscriber;
import com.interview.parkinglotspring.observers.OnVehicleExitSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotService {
    private final List<OnVehicleEntrySubscriber> onVehicleEntrySubscribers;
    private final List<OnVehicleExitSubscriber> onVehicleExitSubscribers;
    private FloorService floorService;
    private VehicleService vehicleService;

    @Autowired
    public ParkingLotService(VehicleService vehicleService, FloorService floorService) {
        this.vehicleService = vehicleService;
        this.floorService = floorService;
        this.onVehicleEntrySubscribers = new ArrayList<>();
        onVehicleEntrySubscribers.add(vehicleService);
        onVehicleEntrySubscribers.add(floorService);
        onVehicleExitSubscribers = new ArrayList<>();
        onVehicleExitSubscribers.add(vehicleService);

    }

    public void onVehicleEntry(Ticket ticket){
        for(OnVehicleEntrySubscriber subscriber : onVehicleEntrySubscribers){
            subscriber.onVehicleEntry(ticket);
        }
    }

    public void onVehicleExit(Bill bill){
        for(OnVehicleExitSubscriber subscriber : onVehicleExitSubscribers){
            subscriber.onVehicleExit(bill);
        }
    }
}
