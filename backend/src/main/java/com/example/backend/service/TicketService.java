package com.example.backend.service;

import com.example.backend.dto.TicketRequest;
import com.example.backend.dto.TicketResponse;
import com.example.backend.models.*;
import com.example.backend.repository.AccountRepository;
import com.example.backend.repository.EventRepository;
import com.example.backend.repository.TicketRepository;
import com.example.backend.repository.TicketTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final AccountRepository accountRepository;

    public List<Ticket> getTicket() {
        return ticketRepository.findAll();
    }

    public ResponseEntity<TicketResponse> registerTicket(Authentication authentication, TicketRequest request) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findByEmail(userDetails.getUsername()).orElse(null);
        TicketResponse ticket = ticketRepository.save(map(request, account)).toResponse();
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }


    private Ticket map(TicketRequest request, Account account) {
        return Ticket.builder()
                .event(eventRepository.findById(request.getEventId()).orElseThrow(() -> new EntityNotFoundException("Invalid Event")))
                .type(ticketTypeRepository.findById(request.getTicketTypeId()).orElseThrow(() -> new EntityNotFoundException("Invalid Ticket Type")))
                .account(account)
                .build();
    }

}
