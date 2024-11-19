package com.example.backend.dto;

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
    private String code;
    private Integer eventId;
    private Integer accountId;
    private TicketTypeDTO type;
    private LocalDateTime purchasedDate;
}
