package com.example.backend.config;

import com.example.backend.dto.EventRequest;
import com.example.backend.models.*;
import com.example.backend.repository.AddressRepository;
import com.example.backend.repository.EventRepository;
import com.example.backend.repository.TagRepository;
import com.example.backend.repository.VendorRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationListener;

import java.time.LocalDateTime;
import java.util.List;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class StartupDataConfig {

    private final ObjectMapper objectMapper;
    private final VendorRepository vendorRepository;
    private final EventRepository eventRepository;
    private final AddressRepository addressRepository;
    private final TagRepository tagRepository;


    @SuppressWarnings("unchecked")
    @PostConstruct
    public void load() throws IOException {
        Path path = Path.of("init.json");
        String json = Files.readString(path);
        List<Map<String, Object>> data = objectMapper.readValue(json, new TypeReference<>() {});

        for(Map<String, Object> vendor : data) {
            Vendor vendorObject = new Vendor(null, vendor.get("name").toString(), vendor.get("address").toString(), null);
            Vendor vendorSaved = vendorRepository.save(vendorObject);
            List<Map<String, Object>> events = (List<Map<String, Object>>) vendor.get("events");
            for(Map<String, Object> event : events) {
                Event eventObject = Event.builder()
                        .name(event.get("name").toString())
                        .description(event.get("description").toString())
                        .time(LocalDateTime.parse(event.get("time").toString()))
                        .customerRetrievalRate((Integer) event.get("customer_retrieval_rate"))
                        .build();
                eventObject.setVendor(vendorSaved);
                eventObject.setAddress(addressRepository.findByAddress(event.get("address").toString()).orElse(new Address(null, event.get("address").toString())));
                eventObject.setMainImage(new Image(null, event.get("main_image_path").toString()));
                eventObject.setCoverImage(
                        Objects.equals(event.get("main_image_path").toString(), event.get("cover_image_path").toString())
                                ? eventObject.getMainImage()
                                : new Image(null, event.get("cover_image_path").toString())
                );
                eventObject.setCategory(Category.valueOf(event.get("category").toString()));
                eventObject.setStatus(Status.valueOf(event.get("status").toString()));
                List<String> tagsObject = (List<String>) event.get("tags");
                List<Tag> tags = tagsObject.stream()
                        .map(tagName -> tagRepository.findById(tagName).orElseGet(() -> new Tag(tagName, null)))
                        .collect(Collectors.toList());
                eventObject.setTags(tags);

                List<Map<String, Object>> ticketTypesObject = (List<Map<String, Object>>) event.get("ticket_types");
                List<TicketType> ticketTypes = ticketTypesObject.stream()
                        .map(ticketType -> new TicketType(null, ticketType.get("type").toString(), (Integer) ticketType.get("price"), (Integer) ticketType.get("total"), (Integer) ticketType.get("max_ticket_capacity"), (Integer) ticketType.get("ticket_release_rate"), ((Integer) ticketType.get("max_ticket_capacity")) * 20/100))
                        .toList();
                eventObject.setTicketTypes(ticketTypes);

                Event eventSaved = eventRepository.save(eventObject);
            }
        }
    }
}
