package com.example.backend.service;

import com.example.backend.models.TicketType;
import com.example.backend.repository.TicketTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketTypeService {
    private final TicketTypeRepository ticketTypeRepository;

    @Transactional
    public void releaseTickets() {
        List<TicketType> ticketTypes = ticketTypeRepository.findAll();
        for(TicketType ticketType : ticketTypes){
            int total = ticketType.getTotal();
            int ticketsBooked = ticketType.getNumberOfTickets();
            int releaseRate = ticketType.getTicketReleaseRate();

            if(ticketsBooked < total){
                int ticketsToRelease = Math.min(releaseRate, total - ticketsBooked);
                ticketType.setTicketReleaseRate(ticketsBooked + ticketsToRelease);
            }
        }

        ticketTypeRepository.saveAll(ticketTypes);
    }
}
