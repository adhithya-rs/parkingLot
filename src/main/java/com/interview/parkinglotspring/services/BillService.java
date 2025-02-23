package com.interview.parkinglotspring.services;

import com.interview.parkinglotspring.dtos.GenerateBillResponseDto;
import com.interview.parkinglotspring.models.Gate;
import com.interview.parkinglotspring.models.enums.ResponseStatus;
import com.interview.parkinglotspring.factories.BillingStrategyFactory;
import com.interview.parkinglotspring.models.Bill;
import com.interview.parkinglotspring.models.Ticket;
import com.interview.parkinglotspring.repositories.BillRepository;
import com.interview.parkinglotspring.repositories.GateRepository;
import com.interview.parkinglotspring.repositories.TicketRepository;
import com.interview.parkinglotspring.strategies.billingStrategy.BillingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BillService {
    private TicketRepository ticketRepository;
    private BillRepository billRepository;
    private GateRepository gateRepository;

    @Autowired
    public BillService(TicketRepository ticketRepository, BillRepository billRepository, GateRepository gateRepository) {
        this.ticketRepository = ticketRepository;
        this.billRepository = billRepository;
        this.gateRepository = gateRepository;
    }

    public GenerateBillResponseDto generateBill(String ticketId, Long gateId) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(Long.parseLong(ticketId));
        if (optionalTicket.isEmpty()) {
            return new GenerateBillResponseDto(null, "Invalid Ticket Number", ResponseStatus.FAILURE);
        }
        Optional<Gate> optionalGate = gateRepository.findById(gateId);
        if (optionalGate.isEmpty()) {
            return new GenerateBillResponseDto(null, "Invalid Gate Number", ResponseStatus.FAILURE);
        }
        Gate gate = optionalGate.get();
        Ticket ticket = optionalTicket.get();
        BillingStrategy billingStrategy = BillingStrategyFactory.getBillingStrategy(ticket.getVehicle().getVehicleType());
        Bill bill = billingStrategy.generateBill(ticket);
        bill.setGate(gate);
        bill = billRepository.save(bill);

        return new GenerateBillResponseDto(bill, "Bill Generated Successfully", ResponseStatus.SUCCESS);
    }
}
