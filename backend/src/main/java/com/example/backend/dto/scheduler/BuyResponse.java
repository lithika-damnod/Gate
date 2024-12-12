package com.example.backend.dto.scheduler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuyResponse {
    private Integer eventId;
    private String eventName;
    private Integer ticketTypeId;
    private String ticketType;
}

