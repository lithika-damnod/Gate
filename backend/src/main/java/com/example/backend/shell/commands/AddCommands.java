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
        String name = inputString("Enter Vendor Name: ");
        String address = inputString("Enter Vendor Address: ");

        Vendor vendor = vendorRepository.save(new Vendor(null, name, address, null));

        System.out.println(new AttributedString("\nVendor Information Summary", AttributedStyle.DEFAULT.underline().bold()).toAnsi());
        System.out.println(
                new AttributedString("Id: ", AttributedStyle.BOLD).toAnsi() + vendor.getId() + "\n" +
                new AttributedString("Vendor Name: ", AttributedStyle.BOLD).toAnsi() + vendor.getName() + "\n" +
                new AttributedString("Address: ", AttributedStyle.BOLD).toAnsi() + vendor.getAddress() + "\n"
        );
    }


    private void addEvent() {
        String name = inputString("Enter Event Name: ");
        String address = inputString("Enter Event Venue: ");
        String description = inputString("Enter Event Description: ");
        LocalDateTime eventDateTime = inputDateTime("Enter the date and time (yyyy/MM/dd HH:mm): ");
        Integer retrievalRate = inputInteger("Enter Customer Retrieval Rate: ");
        Integer vendorId = inputInteger("Enter Vendor Id: ");
        String tags = inputString("Enter tags (comma-seperated): ");

        System.out.println("Select Category:");
        for(int i=0; i< Category.values().length; i++) {
            System.out.println((i + 1) + ". " + Category.values()[i].name());
        }
        Integer category = inputInteger("Enter number of your choice: ");

        String mainImage = inputString("Enter main image url: ");
        String coverImage = inputString("Enter cover image url: ");
        int nTicketTypes = inputInteger("Enter number of ticket types (At least 1): ");

        List<TicketType> ticketTypeList = new ArrayList<>();
        for(int i=0; i<nTicketTypes; i++) {
            TicketType ticketType = new TicketType();

            String ticketTypeName = inputString(String.format("(%d) Enter name of the ticket type: ", i+1));
            ticketType.setType(ticketTypeName);

            Integer ticketPrice = inputInteger("    Enter ticket price: ");
            ticketType.setPrice(ticketPrice);

            Integer total = inputInteger("    Enter total number of tickets: ");
            ticketType.setTotal(total);

            Integer maxTicketCapacity = inputInteger("    Enter max ticket capacity: ");
            ticketType.setMaxTicketCapacity(maxTicketCapacity);

            Integer ticketReleaseRate = inputInteger("    Enter ticket release rate: ");
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

    private String inputString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private Integer inputInteger(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private LocalDateTime inputDateTime(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        while(true) {
            System.out.print(prompt);
            String time = scanner.nextLine();
            try {
                return LocalDateTime.parse(time, formatter);
            }
            catch(Exception e) {
                System.out.println("Invalid date and time format. Please use 'yyyy/MM/dd HH:mm'.");
            }
        }
    }
}
