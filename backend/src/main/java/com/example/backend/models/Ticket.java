package com.example.backend.models;

import com.example.backend.dto.AccountDTOMapper;
import com.example.backend.dto.TicketResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table()
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType type;

    @Column(updatable = false)
    private LocalDateTime purchasedDate;

    @PrePersist
    public void prePersist() {
        this.purchasedDate = LocalDateTime.now();
    }

    public TicketResponse toResponse() {
        return TicketResponse.builder()
                .id(this.id)
                .event(this.event.toResponse())
                .account(new AccountDTOMapper().apply(this.account))
                .type(this.type)
                .purchasedDate(this.purchasedDate)
                .build();
    }
}
