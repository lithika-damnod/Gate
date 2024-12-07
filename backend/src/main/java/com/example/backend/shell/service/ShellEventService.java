package com.example.backend.shell.service;

import com.example.backend.dto.EventResponse;
import com.example.backend.models.Event;
import com.example.backend.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ShellEventService {

    private final EventRepository eventRepository;

    @Transactional
    public void get(Integer id) {
        if (id == null) {
            List<EventResponse> events = eventRepository.findAll()
                    .stream()
                    .map(Event::toResponse)
                    .toList();

            String[] headers = {
                    "ID",
                    "Event Name",
                    "Address",
                    "Time",
                    "Retrieval Rate",
                    "Category",
                    "Status"
            };

            int[] maxWidths = Arrays.stream(headers)
                    .mapToInt(String::length)
                    .toArray();

            for (EventResponse event : events) {
                maxWidths[0] = Math.max(maxWidths[0], event.getId().toString().length());
                maxWidths[1] = Math.max(maxWidths[1], event.getName().length());
                maxWidths[2] = Math.max(maxWidths[2], event.getAddress().getAddress().length());
                maxWidths[3] = Math.max(maxWidths[3], event.getTime().toString().length());
                maxWidths[4] = Math.max(maxWidths[4], event.getCustomerRetrievalRate().toString().length());
                maxWidths[5] = Math.max(maxWidths[5], event.getCategory().toString().length());
                maxWidths[6] = Math.max(maxWidths[6], event.getStatus().toString().length());
            }

            String header = String.format("%-" +
                    Math.max(1, maxWidths[0]+1) + "s | %-" +
                    Math.max(1, maxWidths[1]) + "s | %-" +
                    Math.max(1, maxWidths[2]) + "s | %-" +
                    Math.max(1, maxWidths[3]) + "s | %-" +
                    Math.max(1, maxWidths[4]) + "s | %-" +
                    Math.max(1, maxWidths[5]) + "s | %-" +
                    Math.max(1, maxWidths[6]) + "s\n", (Object[]) headers);
            System.out.print(header);
            System.out.println("-" + "-".repeat(header.length()));
            for (EventResponse event : events) {
                System.out.printf("%-" +
                                (Math.max(1, maxWidths[0]) + 1) + "s | %-" +
                                (Math.max(1, maxWidths[1])) + "s | %-" +
                                (Math.max(1, maxWidths[2])) + "s | %-" +
                                (Math.max(1, maxWidths[3])) + "s | %-" +
                                (Math.max(1, maxWidths[4])) + "s | %-" +
                                (Math.max(1, maxWidths[5])) + "s | %-" +
                                (Math.max(1, maxWidths[6]) + 1) + "s\n",
                        event.getId(), event.getName(), event.getAddress().getAddress(), event.getTime().toString(), event.getCustomerRetrievalRate().toString(), event.getCategory().toString(), event.getStatus().toString());
            }
        } else {
            Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entered Id is not valid!"));
            System.out.println(new AttributedString("Id: ", AttributedStyle.BOLD).toAnsi() + event.getId() + "\n" +
                    new AttributedString("Event Name: ", AttributedStyle.BOLD).toAnsi() + event.getName() + "\n" +
                    new AttributedString("Category: ", AttributedStyle.BOLD).toAnsi() + event.getCategory() + "\n" +
                    new AttributedString("Description: ", AttributedStyle.BOLD).toAnsi() + event.getDescription());
        }
    }
}