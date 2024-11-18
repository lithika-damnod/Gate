package com.example.backend.dto;

import com.example.backend.models.Account;
import com.example.backend.models.Event;
import com.example.backend.models.TicketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    Integer eventId;
    Integer ticketTypeId;
}
