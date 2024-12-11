package com.example.backend.service;

import com.example.backend.models.TicketType;
import com.example.backend.repository.TicketTypeRepository;
import com.example.backend.websocket.CustomWebSocketHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TicketTypeService {
    private final TicketTypeRepository ticketTypeRepository;
    private final CustomWebSocketHandler customWebSocketHandler;

    @Transactional
    public void releaseTickets() {
        List<TicketType> ticketTypes = ticketTypeRepository.findAll();
        for(TicketType ticketType : ticketTypes){
            int total = ticketType.getTotal();
            int pool = ticketType.getNumberOfTickets();
            int releaseRate = ticketType.getTicketReleaseRate();
            int maxTicketCapacity =  ticketType.getMaxTicketCapacity();

            /*
            if(pool < total){
                int ticketsToRelease = Math.min(releaseRate, maxTicketCapacity - pool);
                if((ticketsToRelease+pool) > total) {
                    ticketsToRelease = total;
                }
                ticketType.setNumberOfTickets(pool + ticketsToRelease);
            }
            */

            if(pool < maxTicketCapacity && total > 0) { // if total is zero there's nothing possible to be done
                int nTicketsToRelease = Math.min(releaseRate, maxTicketCapacity - pool);
                if(total >= nTicketsToRelease) { // there's enough tickets left to be added to the pool
                    total-=nTicketsToRelease;
                    pool+=nTicketsToRelease;
                }
                else {
                    pool += total;
                    total = 0;
                }

                ticketType.setNumberOfTickets(pool);
                ticketType.setTotal(total);
                TicketType saved = ticketTypeRepository.save(ticketType);

                // releasing
                Map<String, Object> returnMsg = new HashMap<>();
                returnMsg.put("release", saved);
                customWebSocketHandler.broadcast(returnMsg);
            }

        }
    }
}
