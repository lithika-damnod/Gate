package com.example.backend.service;

import com.example.backend.dto.TicketRequest;
import com.example.backend.dto.TicketResponse;
import com.example.backend.dto.scheduler.BuyResponse;
import com.example.backend.models.*;
import com.example.backend.repository.AccountRepository;
import com.example.backend.repository.EventRepository;
import com.example.backend.repository.TicketRepository;
import com.example.backend.repository.TicketTypeRepository;
import com.example.backend.shell.commands.LogCommands;
import com.example.backend.websocket.CustomWebSocketHandler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final AccountRepository accountRepository;
    private final CustomWebSocketHandler customWebSocketHandler;

    public List<TicketResponse> getTicket() {
        return ticketRepository.findAll().stream().map(Ticket::toResponse).collect(Collectors.toList());
    }

    public TicketResponse getTicket(String code) {
        return ticketRepository.findByCode(code).map(Ticket::toResponse).orElseThrow(() -> new EntityNotFoundException("Ticket Not Found"));
    }

    public synchronized ResponseEntity<TicketResponse> registerTicket(Authentication authentication, TicketRequest request) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findByEmail(userDetails.getUsername()).orElse(null);

        Ticket requestTicket = map(request, account);
        TicketType ticketType = requestTicket.getType();

        // check if the ticket type is available for booking
        if(ticketType.getNumberOfTickets() > 0 && requestTicket.getEvent().getStatus() != Status.INACTIVE) {
            // decrease the number of tickets within ticket type
            ticketType.setNumberOfTickets(ticketType.getNumberOfTickets() - 1);
            ticketTypeRepository.save(ticketType);

            // save ticket booking
            TicketResponse ticket = ticketRepository.save(requestTicket).toResponse();


            // send updates
            Event event = eventRepository.findById(ticket.getEventId()).orElseThrow();
            // websocket
            Map<String, Object> returnMsg = new HashMap<>();
            returnMsg.put("buy", BuyResponse.builder()
                            .eventId(event.getId())
                            .eventName(event.getName())
                            .ticketTypeId(ticketType.getId())
                            .ticketType(ticketType.getType())
                    .build());
            customWebSocketHandler.broadcast(returnMsg);

            // log
            LogCommands.logMessage("[BUY] Type: " + ticketType.getType() + " Event: " + event.getName() + " (Id: " + ticket.getEventId() + ")");
            return new ResponseEntity<>(ticket, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN); // return error if anything goes wrong!
    }

    public synchronized void deleteTicket(String code) {
        Ticket ticket = ticketRepository.findByCode(code.toUpperCase()).orElseThrow(() -> new EntityNotFoundException("Ticket Not Found"));
        ticketRepository.delete(ticket);
    }


    private Ticket map(TicketRequest request, Account account) {
        return Ticket.builder()
                .event(eventRepository.findById(request.getEventId()).orElseThrow(() -> new EntityNotFoundException("Invalid Event")))
                .type(ticketTypeRepository.findById(request.getTicketTypeId()).orElseThrow(() -> new EntityNotFoundException("Invalid Ticket Type")))
                .account(account)
                .build();
    }

}
