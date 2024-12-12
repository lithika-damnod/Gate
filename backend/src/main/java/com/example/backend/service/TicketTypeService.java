package com.example.backend.service;

import com.example.backend.dto.scheduler.ReleaseResponse;
import com.example.backend.models.TicketType;
import com.example.backend.repository.TicketTypeRepository;
import com.example.backend.shell.commands.LogCommands;
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

                // releasing websocket
                ReleaseResponse releaseResponse = ReleaseResponse.builder()
                        .id(saved.getId())
                        .ticketsReleased(nTicketsToRelease)
                        .pool(saved.getNumberOfTickets())
                        .total(saved.getTotal())
                        .maxTicketCapacity(saved.getMaxTicketCapacity())
                        .build();

                Map<String, Object> returnMsg = new HashMap<>();
                returnMsg.put("release", releaseResponse);
                customWebSocketHandler.broadcast(returnMsg);

                // releasing log
                LogCommands.logMessage("[RELEASE] " + nTicketsToRelease + " Tickets were released on Ticket Type Pool: " + ticketType.getType() + " (Id: " + ticketType.getId() + ")");
            }
        }
    }
}
