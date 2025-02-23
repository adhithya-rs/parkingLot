package com.interview.parkinglotspring.repositories;

import com.interview.parkinglotspring.models.Ticket;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@Repository
public class TicketRepository {
    private static Map<Long, Ticket> ticketMap = new HashMap<>();
    private Long ticketId = 0L;

    public Optional<Ticket> findById(long id) {
        return Optional.ofNullable(ticketMap.get(id));
    }

    public Ticket save(Ticket ticket) {
        if(ticketMap.get(ticket.getId()) == null) {
            ticketId +=1;
            ticket.setId(ticketId);
            ticketMap.put(ticket.getId(), ticket);
        }else{
            ticketMap.put(ticket.getId(), ticket);
        }
        return ticket;
    }
}
