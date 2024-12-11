package com.example.backend.service;

import com.example.backend.config.GlobalConfig;
import com.example.backend.websocket.CustomWebSocketHandler;
import jakarta.annotation.PostConstruct;
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
    private final GlobalConfig globalConfig;

    public Integer rate;

    @PostConstruct
    public void init() {
        rate = globalConfig.getUserRetrievalRate();
    }

    @Scheduled(fixedRate = 10000)
    public void scheduleTicketRelease() {
        ticketTypeService.releaseTickets();
        /*
        Map<String, Object> returnMsg = new HashMap<>();
        returnMsg.put("release", "tickets");

        customWebSocketHandler.broadcast(returnMsg);
         */
    }
}
