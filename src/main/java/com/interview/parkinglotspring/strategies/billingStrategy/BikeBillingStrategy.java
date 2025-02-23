package com.interview.parkinglotspring.strategies.billingStrategy;

import com.interview.parkinglotspring.models.Bill;
import com.interview.parkinglotspring.models.Ticket;
import com.interview.parkinglotspring.models.enums.BillStatus;

import java.util.Date;

public class BikeBillingStrategy implements BillingStrategy {
    private static final Long FIRST_HOUR_FEE = 50L;
    private static final Long ADDITIONAL_HOUR_FEE = 20L;

    @Override
    public Bill generateBill(Ticket ticket) {
        Date exitTime = new Date(); // Assuming exit happens now
        long durationInMillis = exitTime.getTime() - ticket.getEntryTime().getTime();
        long durationInHours = (long) Math.ceil(durationInMillis / (1000.0 * 60 * 60)); // Convert to hours

        long totalAmount;
        if (durationInHours <= 1) {
            totalAmount = FIRST_HOUR_FEE;
        } else {
            totalAmount = FIRST_HOUR_FEE + (int) ((durationInHours - 1) * ADDITIONAL_HOUR_FEE);
        }

        Bill bill = new Bill();
        bill.setTicket(ticket);
        bill.setExitTime(exitTime);
        bill.setStatus(BillStatus.UNPAID);
        bill.setPayments(null); // As per your instruction
        bill.setAmount(totalAmount);
        System.out.println("Bike Parking Duration: " + durationInHours + " hours, Bill Amount: Rs. " + totalAmount);
        return bill;
    }
}
