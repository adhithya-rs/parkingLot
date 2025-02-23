package com.interview.parkinglotspring.observers;

import com.interview.parkinglotspring.models.Bill;
import com.interview.parkinglotspring.models.Vehicle;

public interface OnVehicleExitSubscriber {

    public void onVehicleExit(Bill bill);
}
