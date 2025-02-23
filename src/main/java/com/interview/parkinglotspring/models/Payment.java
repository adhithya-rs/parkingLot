package com.interview.parkinglotspring.models;

import com.interview.parkinglotspring.models.enums.PaymentMode;
import com.interview.parkinglotspring.models.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Payment extends BaseClass{
    private PaymentMode paymentMode;
    private double paymentAmount;
    private String referenceId;
    private PaymentStatus paymentStatus;
    private Date paymentDate;
}
