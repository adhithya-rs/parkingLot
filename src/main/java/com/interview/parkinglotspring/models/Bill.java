package com.interview.parkinglotspring.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Bill extends BaseClass{
    private List<Payment> payments;
    private Gate gate;
    private Ticket ticket;
    private Operator operator;
    private Date exitTime;
    private BillStatus status;
}
