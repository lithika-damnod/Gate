package com.example.backend.dto;

import com.example.backend.models.Vendor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorRequest {

    @Valid

    @NotBlank(message="vendor name is required")
    private String name;

    @NotBlank(message="address is required")
    private String address;

    public Vendor map() {
        return new Vendor(null, name, address);
    }
}
