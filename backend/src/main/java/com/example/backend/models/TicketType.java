package com.example.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table()
public class TicketType {
    @Id
    @SequenceGenerator(
            name = "ticket_type_sequence",
            sequenceName = "ticket_type_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ticket_type_sequence"
    )
    Integer id;
    String type;
    Integer price;
}
