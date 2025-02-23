package com.interview.parkinglotspring.factories;

import com.interview.parkinglotspring.models.enums.VehicleType;
import com.interview.parkinglotspring.strategies.billingStrategy.BikeBillingStrategy;
import com.interview.parkinglotspring.strategies.billingStrategy.BillingStrategy;
import com.interview.parkinglotspring.strategies.billingStrategy.CarBillingStrategy;

public class BillingStrategyFactory {

    public static BillingStrategy getBillingStrategy(VehicleType vehicleType) {
        if(vehicleType == VehicleType.CAR) {
            return new CarBillingStrategy();
        }else{
            return new BikeBillingStrategy();
        }
    }
}
