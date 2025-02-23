package com.interview.parkinglotspring.models;

import com.interview.parkinglotspring.models.enums.GateStatus;
import com.interview.parkinglotspring.models.enums.GateType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Gate extends BaseClass{
    private Operator operator;
    private GateType gateType;
    private GateStatus status;

    public Gate(String name, GateType gateType) {
        this.operator = new Operator(name);
        this.gateType = gateType;
        this.status = GateStatus.FUNCTIONAL;
    }

}
