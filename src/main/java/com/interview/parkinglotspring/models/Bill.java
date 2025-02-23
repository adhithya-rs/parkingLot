package com.interview.parkinglotspring.models;

import com.interview.parkinglotspring.models.enums.BillStatus;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Bill extends BaseClass{
    private List<Payment> payments;
    private Gate gate;
    private Ticket ticket;
    private Date exitTime;
    private BillStatus status;
    private Long amount;

    public void printBill(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Define readable date format
        String formattedExitTime = (exitTime != null) ? sdf.format(exitTime) : "N/A"; // Handle null case

        System.out.println("\nBill ID: " + getId()
                + "\nVehicle Number: " + ticket.getVehicle().getLicensePlate()
                + "\nOwner Name: " + ticket.getVehicle().getOwnerName()
                + "\nEntry Time: " + sdf.format(ticket.getEntryTime())  // Format entry time
                + "\nExit Time: " + formattedExitTime // Format exit time or show "N/A"
                + "\nTotal Amount: Rs. " + amount
                + "\nPayment Status: " + status
                + "\nPayment Details: ");

        if (payments == null || payments.isEmpty()) {
            System.out.println("No payments recorded.");
        } else {
            for (Payment payment : payments) {
                System.out.println("  - Payment Mode: " + payment.getPaymentMode()
                        + ", Amount: Rs. " + payment.getPaymentAmount()
                        + ", Status: " + payment.getPaymentStatus()
                        + ", Ref ID: " + payment.getReferenceId());
            }
        }

        System.out.println("\nBill Generated at Gate: " + gate.getId()
                + " by Operator: " + gate.getOperator().getName()
                + "\n");
    }
}
