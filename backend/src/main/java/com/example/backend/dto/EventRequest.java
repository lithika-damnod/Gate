package com.example.backend.dto;

import com.example.backend.models.*;
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
public class EventRequest {
    // internals
    private String name;
    private String description;
    private LocalDateTime time;
    private Integer customerRetrievalRate;

    // externals
    private Integer vendorId;
    private String address;
    private String mainImagePath;
    private String coverImagePath;
    private Category category;
    private Status status;
    private List<String> tags;
}