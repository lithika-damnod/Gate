package com.example.backend.models;

import com.example.backend.dto.TicketResponse;
import com.example.backend.dto.TicketTypeDTO;
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

    @Column(unique  = true, nullable = false)
    private String code;

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
        this.code = this.id.toString().substring(0, 8).toUpperCase();
    }

    public TicketResponse toResponse() {
        return TicketResponse.builder()
                .id(this.id)
                .code(this.code)
                .eventId(this.event.getId())
                .accountId(this.account.getId())
                .type(new TicketTypeDTO(this.type.getType(), this.type.getPrice(), this.type.getTotal(), this.type.getMaxTicketCapacity(), this.type.getTicketReleaseRate(), this.type.getNumberOfTickets()))
                .purchasedDate(this.purchasedDate)
                .build();
    }
}
