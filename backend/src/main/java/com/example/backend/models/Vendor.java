package com.example.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
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
public class Vendor {
    @Id
    @SequenceGenerator(
            name = "vendor_sequence",
            sequenceName = "vendor_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vendor_sequence"
    )
    private Integer id;
    private String name;
    private String address;

    public void update(String name, String address) {
        if(name != null) this.name = name;
        if(address != null) this.address = address;
    }
}
