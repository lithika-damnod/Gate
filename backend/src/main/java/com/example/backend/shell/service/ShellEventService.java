package com.example.backend.shell.service;

import com.example.backend.dto.EventResponse;
import com.example.backend.models.*;
import com.example.backend.repository.AddressRepository;
import com.example.backend.repository.EventRepository;
import com.example.backend.repository.TagRepository;
import com.example.backend.repository.VendorRepository;
import com.example.backend.util.InputUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShellEventService {

    private final EventRepository eventRepository;
    private final VendorRepository vendorRepository;
    private final TagRepository tagRepository;
    private final AddressRepository addressRepository;

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


    public void add() {
        String name = InputUtils.inputString("Enter Event Name: ");
        String address = InputUtils.inputString("Enter Event Venue: ");
        String description = InputUtils.inputString("Enter Event Description: ");
        LocalDateTime eventDateTime = InputUtils.inputDateTime("Enter the date and time (yyyy/MM/dd HH:mm): ");
        Integer retrievalRate = InputUtils.inputInteger("Enter Customer Retrieval Rate: ");
        Integer vendorId = InputUtils.inputInteger("Enter Vendor Id: ");
        String tags = InputUtils.inputString("Enter tags (comma-seperated): ");

        System.out.println("Select Category:");
        for(int i = 0; i< Category.values().length; i++) {
            System.out.println((i + 1) + ". " + Category.values()[i].name());
        }
        Integer category = InputUtils.inputInteger("Enter number of your choice: ");

        String mainImage = InputUtils.inputString("Enter main image url: ");
        String coverImage = InputUtils.inputString("Enter cover image url: ");
        int nTicketTypes = InputUtils.inputInteger("Enter number of ticket types (At least 1): ");

        List<TicketType> ticketTypeList = new ArrayList<>();
        for(int i=0; i<nTicketTypes; i++) {
            TicketType ticketType = new TicketType();

            String ticketTypeName = InputUtils.inputString(String.format("(%d) Enter name of the ticket type: ", i+1));
            ticketType.setType(ticketTypeName);

            Integer ticketPrice = InputUtils.inputInteger("    Enter ticket price: ");
            ticketType.setPrice(ticketPrice);

            Integer total = InputUtils.inputInteger("    Enter total number of tickets: ");
            ticketType.setTotal(total);

            Integer maxTicketCapacity = InputUtils.inputInteger("    Enter max ticket capacity: ");
            ticketType.setMaxTicketCapacity(maxTicketCapacity);

            Integer ticketReleaseRate = InputUtils.inputInteger("    Enter ticket release rate: ");
            ticketType.setTicketReleaseRate(ticketReleaseRate);

            ticketType.setNumberOfTickets(ticketType.getMaxTicketCapacity() * 20/100);

            ticketTypeList.add(ticketType);
        }

        Event event = Event.builder()
                .id(null)
                .name(name)
                .description(description)
                .time(eventDateTime)
                .customerRetrievalRate(retrievalRate)
                .vendor(vendorRepository.findById(vendorId).orElseThrow(() -> new EntityNotFoundException("Invalid Vendor")))
                .address(addressRepository.findByAddress(address).orElse(new Address(null, address)))
                .mainImage(new Image(null, mainImage))
                .category(Category.values()[category-1])
                .ticketTypes(ticketTypeList)
                .status(Status.ACTIVE)
                .build();

        event.setCoverImage(
                Objects.equals(mainImage, coverImage)
                        ? event.getMainImage()
                        : new Image(null, coverImage)
        );

        List<Tag> tagList = Arrays.stream(tags.split(",", -1))
                .map(String::trim)
                .map(tagName -> tagRepository.findById(tagName).orElseGet(() -> new Tag(tagName, null)))
                .collect(Collectors.toList());
        event.setTags(tagList);

        eventRepository.save(event);

        System.out.println();
    }
}