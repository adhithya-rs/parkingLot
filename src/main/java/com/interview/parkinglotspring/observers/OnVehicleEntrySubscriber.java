package com.interview.parkinglotspring.observers;

import com.interview.parkinglotspring.models.Ticket;
import com.interview.parkinglotspring.models.Vehicle;

public interface OnVehicleEntrySubscriber {

    void onVehicleEntry(Ticket ticket);
}
