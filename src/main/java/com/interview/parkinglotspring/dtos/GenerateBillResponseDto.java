package com.interview.parkinglotspring.dtos;

import com.interview.parkinglotspring.models.Bill;
import com.interview.parkinglotspring.models.enums.ResponseStatus;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GenerateBillResponseDto {
    private Bill bill;
    private String message;
    private ResponseStatus responseStatus;

    public GenerateBillResponseDto(Bill bill, String message, ResponseStatus responseStatus) {
        this.bill = bill;
        this.message = message;
        this.responseStatus = responseStatus;
    }
}
