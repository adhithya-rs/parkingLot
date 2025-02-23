package com.interview.parkinglotspring.services;

import com.interview.parkinglotspring.models.Bill;
import com.interview.parkinglotspring.models.Ticket;
import com.interview.parkinglotspring.observers.OnVehicleEntrySubscriber;
import com.interview.parkinglotspring.observers.OnVehicleExitSubscriber;
import org.springframework.stereotype.Service;

@Service
public class VehicleService implements OnVehicleEntrySubscriber, OnVehicleExitSubscriber {
    @Override
    public void onVehicleEntry(Ticket ticket) {
        ticket.getVehicle().setParked(true);

    }

    @Override
    public void onVehicleExit(Bill bill) {
        bill.getTicket().getVehicle().setParked(false);
    }
}
