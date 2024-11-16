package com.example.backend.controller;

import com.example.backend.dto.EventRequest;
import com.example.backend.dto.EventResponse;
import com.example.backend.repository.EventRepository;
import com.example.backend.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/event")
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventResponse>> getEvent() {
        return ResponseEntity.ok(eventService.getEvent());
    }

    @GetMapping("{id}")
    public ResponseEntity<EventResponse> getEvent(@PathVariable Integer id) {
        return ResponseEntity.ok(eventService.getEvent(id));
    }

    @PostMapping
    public ResponseEntity<EventResponse> addEvent(@RequestBody EventRequest request) {
        return ResponseEntity.ok(eventService.addEvent(request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") Integer id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build(); // 204: No Content Status
    }


}