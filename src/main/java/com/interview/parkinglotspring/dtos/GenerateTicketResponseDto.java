package com.interview.parkinglotspring.dtos;

import com.interview.parkinglotspring.models.Ticket;
import com.interview.parkinglotspring.models.enums.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateTicketResponseDto {
    private Ticket ticket;
    private String message;
    private ResponseStatus responseStatus;

    public GenerateTicketResponseDto(Ticket ticket, String message, ResponseStatus responseStatus) {
        this.ticket = ticket;
        this.message = message;
        this.responseStatus = responseStatus;
    }

}
