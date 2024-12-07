package com.example.backend.shell.commands;

import com.example.backend.models.*;
import com.example.backend.repository.AddressRepository;
import com.example.backend.repository.EventRepository;
import com.example.backend.repository.TagRepository;
import com.example.backend.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class AddCommands {

    private final Scanner scanner = new Scanner(System.in);
    private final VendorRepository vendorRepository;
    private final EventRepository eventRepository;
    private final AddressRepository addressRepository;
    private final TagRepository tagRepository;

    @ShellMethod(key="add", value="Add information about (vendor, event)")
    public void add(
        @ShellOption(help="Type to insert (vendor, event)") String type
    ) {
        if(Objects.equals(type, "vendor")) addVendor();
        else if(Objects.equals(type, "event")) addEvent();
        else System.out.println("\u001B[31mInvalid type. Valid options are: vendor, event.\u001B[0m");
    }

    private void addVendor() {
        System.out.print("Enter Vendor Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Vendor Address: ");
        String address = scanner.nextLine();

        Vendor vendor = vendorRepository.save(new Vendor(null, name, address, null));

        System.out.println(new AttributedString("\nVendor Information Summary", AttributedStyle.DEFAULT.underline().bold()).toAnsi());
        System.out.println(
                new AttributedString("Id: ", AttributedStyle.BOLD).toAnsi() + vendor.getId() + "\n" +
                new AttributedString("Vendor Name: ", AttributedStyle.BOLD).toAnsi() + vendor.getName() + "\n" +
                new AttributedString("Address: ", AttributedStyle.BOLD).toAnsi() + vendor.getAddress() + "\n"
        );
    }

    private void addEvent() {
        System.out.print("Enter Event Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Event Venue: ");
        String address = scanner.nextLine();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

        System.out.print("Enter the date and time (yyyy/MM/dd HH:mm): ");
        String time = scanner.nextLine();
        LocalDateTime eventDateTime = null;
        try {
            eventDateTime = LocalDateTime.parse(time, formatter);
        }
        catch(Exception e) {
            System.out.println("Invalid date and time format. Please use 'dd/MM/yyyy HH:mm'.");
        }


        System.out.print("Enter Customer Retrieval Rate: ");
        Integer retrievalRate = scanner.nextInt();

        System.out.print("Enter Vendor Id: ");
        Integer vendorId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Event Description: ");
        String description = scanner.nextLine();

        System.out.print("Enter tags (comma-seperated): ");
        String tags = scanner.nextLine();

        System.out.println("Select Category:");
        for(int i=0; i< Category.values().length; i++) {
            System.out.println((i + 1) + ". " + Category.values()[i].name());
        }
        System.out.print("Enter number of your choice: ");
        int category = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter main image url: ");
        String mainImage = scanner.nextLine();

        System.out.print("Enter cover image url: ");
        String coverImage = scanner.nextLine();

        System.out.print("Number of Ticket Types (At least 1): ");
        int nTicketTypes = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Number of Ticket Types: " + nTicketTypes);

        List<TicketType> ticketTypeList = new ArrayList<>();
        for(int i=0; i<nTicketTypes; i++) {
            TicketType ticketType = new TicketType();

            System.out.printf(String.format("(%d) Enter name of the ticket type: ", i+1));
            String ticketTypeName = scanner.nextLine();
            ticketType.setType(ticketTypeName);

            System.out.print("    Enter ticket price: ");
            Integer ticketPrice = scanner.nextInt();
            scanner.nextLine();
            ticketType.setPrice(ticketPrice);

            System.out.print("    Enter total number of tickets: ");
            Integer total = scanner.nextInt();
            scanner.nextLine();
            ticketType.setTotal(total);

            System.out.print("    Enter max ticket capacity: ");
            Integer maxTicketCapacity = scanner.nextInt();
            scanner.nextLine();
            ticketType.setMaxTicketCapacity(maxTicketCapacity);

            System.out.print("    Enter ticket release rate: ");
            Integer ticketReleaseRate = scanner.nextInt();
            scanner.nextLine();
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
