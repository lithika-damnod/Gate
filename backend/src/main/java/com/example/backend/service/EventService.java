package com.example.backend.service;

import com.example.backend.dto.EventRequest;
import com.example.backend.dto.EventResponse;
import com.example.backend.dto.scheduler.BuyResponse;
import com.example.backend.dto.scheduler.WebSocketEventResponse;
import com.example.backend.models.*;
import com.example.backend.repository.*;
import com.example.backend.websocket.CustomWebSocketHandler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final VendorRepository vendorRepository;
    private final AddressRepository addressRepository;
    private final TagRepository tagRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final CustomWebSocketHandler customWebSocketHandler;

    public List<EventResponse> getEvent() {
        return eventRepository.findAll()
                .stream()
                .map(Event::toResponse)
                .collect(Collectors.toList());
    }

    public EventResponse getEvent(Integer id) {
        return eventRepository.findById(id)
                .map(Event::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Event Not Found"));
    }

    @Async
    public CompletableFuture<EventResponse> addEvent(EventRequest request) {
        Event event = map(request);
        Event saved = eventRepository.save(event);

        // send websocket updates
        Map<String, Object> returnMsg = new HashMap<>();
        returnMsg.put("event", WebSocketEventResponse.builder()
                .eventId(saved.getId())
                .eventName(saved.getName())
                .build());
        customWebSocketHandler.broadcast(returnMsg);
        return CompletableFuture.completedFuture(saved.toResponse());
    }

    public synchronized void deleteEvent(Integer id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event Not Found"));

        eventRepository.delete(event);
    }

    private Event map(EventRequest request) {
        Event event = Event.builder()
                .name(request.getName()) // TODO: Handle Duplicate Event Creation Here
                .description(request.getDescription())
                .time(request.getTime())
                .customerRetrievalRate(request.getCustomerRetrievalRate())
                .build();
        event.setVendor(vendorRepository.findById(request.getVendorId()).orElseThrow(() -> new EntityNotFoundException("Invalid Vendor")));
        event.setAddress(addressRepository.findByAddress(request.getAddress()).orElse(new Address(null, request.getAddress())));
        event.setMainImage(new Image(null, request.getMainImagePath()));
        event.setCoverImage(
                Objects.equals(request.getMainImagePath(), request.getCoverImagePath())
                ? event.getMainImage()
                : new Image(null, request.getCoverImagePath())
        );
        event.setCategory(request.getCategory());
        event.setStatus(request.getStatus());
        List<Tag> tags = request.getTags().stream()
                .map(tagName -> tagRepository.findById(tagName).orElseGet(() -> new Tag(tagName, null)))
                .collect(Collectors.toList());
        event.setTags(tags);

        List<TicketType> ticketTypes = request.getTicketTypes().stream()
                .map(ticketType -> new TicketType(null, ticketType.getType(), ticketType.getPrice(), ticketType.getTotal(), ticketType.getMaxTicketCapacity(), ticketType.getTicketReleaseRate(), ticketType.getMaxTicketCapacity()))
                .toList();
        event.setTicketTypes(ticketTypes);

        return event;
    }
}


