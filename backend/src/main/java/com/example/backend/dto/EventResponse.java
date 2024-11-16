package com.example.backend.dto;

import com.example.backend.models.*;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime time;
    private Integer customerRetrievalRate;
    private Address address;
    private Image mainImage;
    private Image coverImage;
    private Category category;
    private Status status;
    private VendorDTO vendor;
    private List<String> tags;
}


