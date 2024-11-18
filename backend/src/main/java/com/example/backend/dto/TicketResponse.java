package com.example.backend.dto;

import com.example.backend.models.Account;
import com.example.backend.models.Event;
import com.example.backend.models.TicketType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private UUID id;
    private EventResponse event;
    private AccountDTO account;
    private TicketType type;
    private LocalDateTime purchasedDate;
}
