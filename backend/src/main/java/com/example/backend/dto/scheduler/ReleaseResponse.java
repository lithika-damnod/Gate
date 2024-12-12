package com.example.backend.dto.scheduler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseResponse {
    private Integer id;
    private Integer ticketsReleased;
    private Integer total;
    private Integer pool;
    private Integer maxTicketCapacity;
}
