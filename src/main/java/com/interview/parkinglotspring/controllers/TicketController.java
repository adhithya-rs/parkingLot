package com.interview.parkinglotspring.controllers;

import com.interview.parkinglotspring.dtos.GenerateTicketRequestDto;
import com.interview.parkinglotspring.dtos.GenerateTicketResponseDto;
import com.interview.parkinglotspring.models.Ticket;
import com.interview.parkinglotspring.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TicketController {
    @Autowired
    private TicketService ticketService;

    public GenerateTicketResponseDto generateTicket(GenerateTicketRequestDto generateTicketRequestDto) {
        return ticketService.generateTicket(generateTicketRequestDto);

    }

}
