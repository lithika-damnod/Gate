package com.example.backend.dto;

import com.example.backend.models.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @Valid

    // internals
    @NotBlank(message = "event name is required")
    private String name;

    private String description;

    @NotNull(message = "event data/time is required")
    @FutureOrPresent(message = "date should be a future or present value")
    private LocalDateTime time;

    @NotNull(message = "customer retrieval rate is required")
    private Integer customerRetrievalRate;

    // externals
    @NotNull(message = "vendor_id is required")
    private Integer vendorId;

    @NotBlank(message = "address is required")
    private String address;

    @NotBlank(message = "main image is required")
    private String mainImagePath;

    @NotBlank(message = "cover image is required")
    private String coverImagePath;

    @NotNull(message = "category should be specified")
    private Category category;

    @NotNull(message = "status should be specified")
    private Status status;

    private List<String> tags;
}