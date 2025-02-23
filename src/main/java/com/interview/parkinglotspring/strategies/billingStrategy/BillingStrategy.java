package com.interview.parkinglotspring.strategies.billingStrategy;

import com.interview.parkinglotspring.models.Bill;
import com.interview.parkinglotspring.models.Ticket;

public interface BillingStrategy {

    Bill generateBill(Ticket ticket);
}
