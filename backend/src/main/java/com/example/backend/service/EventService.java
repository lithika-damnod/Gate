package com.example.backend.service;

import com.example.backend.dto.EventRequest;
import com.example.backend.dto.EventResponse;
import com.example.backend.models.*;
import com.example.backend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final VendorRepository vendorRepository;
    private final AddressRepository addressRepository;
    private final TagRepository tagRepository;
    private final TicketTypeRepository ticketTypeRepository;

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

    public synchronized EventResponse addEvent(EventRequest request) {
        Event event = map(request);
        return eventRepository.save(event).toResponse();
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
                .map(ticketType -> new TicketType(null, ticketType.getType(), ticketType.getPrice(), ticketType.getTotal(), ticketType.getMaxTicketCapacity(), ticketType.getTicketReleaseRate(), ticketType.getMaxTicketCapacity() * 20/100))
                .toList();
        event.setTicketTypes(ticketTypes);

        return event;
    }
}


