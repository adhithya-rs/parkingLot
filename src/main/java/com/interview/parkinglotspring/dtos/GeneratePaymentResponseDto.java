package com.interview.parkinglotspring.dtos;

import com.interview.parkinglotspring.models.Payment;
import com.interview.parkinglotspring.models.enums.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeneratePaymentResponseDto {
    private List<Payment> payments;
    private String message;
    private ResponseStatus responseStatus;

    public GeneratePaymentResponseDto(List<Payment> payments, String message, ResponseStatus responseStatus ) {
        this.payments = payments;
        this.message = message;
        this.responseStatus = responseStatus;
    }
}
