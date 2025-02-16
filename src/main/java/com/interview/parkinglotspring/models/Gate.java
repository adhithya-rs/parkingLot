package com.interview.parkinglotspring.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Gate extends BaseClass{
    private Operator operator;
    private GateType gateType;
    private GateStatus status;
    private long gateNumber;
}
