package com.example.backend.service;

import com.example.backend.websocket.CustomWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TicketReleaseScheduler {
    private final TicketTypeService ticketTypeService;
    private final CustomWebSocketHandler customWebSocketHandler;

    @Scheduled(fixedRate =  10 * 1000)
    public void scheduleTicketRelease() {
        ticketTypeService.releaseTickets();
        Map<String, Object> returnMsg = new HashMap<>();
        returnMsg.put("release", "tickets");

        customWebSocketHandler.broadcast(returnMsg);
    }
}
