package com.interview.parkinglotspring.services;

import com.interview.parkinglotspring.dtos.GeneratePaymentResponseDto;
import com.interview.parkinglotspring.models.Payment;
import com.interview.parkinglotspring.models.enums.PaymentMode;
import com.interview.parkinglotspring.models.enums.PaymentStatus;
import com.interview.parkinglotspring.models.enums.ResponseStatus;
import com.interview.parkinglotspring.singleton.ScannerObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Component
public class PaymentService {

    public GeneratePaymentResponseDto getPayments(Long totalAmount) {
        List<Payment> payments = new ArrayList<>();
        Scanner scanner = ScannerObject.getInstance();

        System.out.println("Total amount to pay: Rs. " + totalAmount);
        System.out.println("Choose payment mode: \n1. Cash \n2. Credit Card \n3. Split Payment (Cash + Credit Card)");

        int choice = scanner.nextInt();
        double remainingAmount = totalAmount;

        switch (choice) {
            case 1:
                // Full Cash Payment
                payments.add(createPayment(PaymentMode.CASH, totalAmount));
                break;

            case 2:
                // Full Credit Card Payment
                payments.add(createPayment(PaymentMode.CREDIT_CARD, totalAmount));
                break;

            case 3:
                // Split Payment
                System.out.print("Enter amount to pay via Cash: Rs. ");
                double cashAmount = scanner.nextDouble();

                if (cashAmount > totalAmount) {
                    System.out.println("Error: Cash amount cannot exceed total bill. Please enter a valid amount.");
                    return getPayments(totalAmount); // Restart payment process
                }

                double creditAmount = totalAmount - cashAmount;
                payments.add(createPayment(PaymentMode.CASH, cashAmount));
                payments.add(createPayment(PaymentMode.CREDIT_CARD, creditAmount));
                break;

            default:
                System.out.println("Invalid choice. Please try again.");
                return getPayments(totalAmount); // Restart payment process
        }

        System.out.println("Payment successful!");
        return new GeneratePaymentResponseDto(payments, "Payment Successfull", ResponseStatus.SUCCESS);
    }

    private Payment createPayment(PaymentMode mode, double amount) {
        Payment payment = new Payment();
        payment.setPaymentMode(mode);
        payment.setPaymentAmount(amount);
        payment.setReferenceId("TXN" + System.currentTimeMillis()); // Generate unique reference ID
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setPaymentDate(new Date());
        return payment;
    }
}