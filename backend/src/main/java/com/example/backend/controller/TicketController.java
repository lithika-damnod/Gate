package com.example.backend.controller;

import com.example.backend.dto.TicketRequest;
import com.example.backend.dto.TicketResponse;
import com.example.backend.models.Ticket;
import com.example.backend.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<Ticket>> getTicket() {
        return ResponseEntity.ok(ticketService.getTicket());
    }

    @PostMapping
    public ResponseEntity<TicketResponse> registerTicket(Authentication authentication, @RequestBody TicketRequest request) {
        return ticketService.registerTicket(authentication, request);
    }
}
