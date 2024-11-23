package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketTypeDTO {
    private String type;
    private Integer price;
    private Integer total;
    private Integer maxTicketCapacity;
    private Integer releaseRate;
}
