package com.example.backend.models;

import com.example.backend.dto.EventResponse;
import com.example.backend.dto.TicketTypeDTO;
import com.example.backend.dto.VendorDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table()
public class Event {
    @Id
    @SequenceGenerator(
            name = "event_sequence",
            sequenceName = "event_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "event_sequence"
    )
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;
    private LocalDateTime time;
    private Integer customerRetrievalRate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "main_image_id")
    private Image mainImage;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cover_image_id")
    private Image coverImage;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "event_ticket_type",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_type_id")
    )
    private List<TicketType> ticketTypes;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "event_tags",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;


    public EventResponse toResponse() {
        return EventResponse.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .time(this.time)
                .customerRetrievalRate(this.customerRetrievalRate)
                .address(this.address)
                .vendor(VendorDTO.builder()
                        .id(this.vendor.getId())
                        .name(this.vendor.getName())
                        .address(this.address.getAddress())
                        .build())
                .mainImage(this.mainImage)
                .coverImage(this.coverImage)
                .category(this.category)
                .ticketTypes(this.ticketTypes.stream().map(ticketType -> new TicketTypeDTO(ticketType.getType(), ticketType.getPrice() ,ticketType.getTotal(), ticketType.getMaxTicketCapacity(), ticketType.getTicketReleaseRate(), ticketType.getNumberOfTickets())).collect(Collectors.toList()))
                .status(this.status)
                .tags(this.tags.stream().map(Tag::getName).collect(Collectors.toList()))
                .build();
    }
}
