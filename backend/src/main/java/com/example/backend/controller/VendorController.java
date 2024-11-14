package com.example.backend.controller;

import com.example.backend.dto.VendorRequest;
import com.example.backend.models.Vendor;
import com.example.backend.service.VendorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/vendor")
@RequiredArgsConstructor
public class VendorController {

    // services
    private final VendorService vendorService;

    @GetMapping
    public ResponseEntity<List<Vendor>> getVendor() {
        return ResponseEntity.ok(vendorService.getVendor());
    }

    @PostMapping
    public ResponseEntity<Vendor> registerVendor(@RequestBody @Valid VendorRequest request) {
        return ResponseEntity.ok(vendorService.registerVendor(request));
    }

    @GetMapping("{id}")
    public ResponseEntity<Vendor> getVendor(@PathVariable Integer id) {
        return ResponseEntity.ok(vendorService.getVendor(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable Integer id, @RequestBody VendorRequest request) {
        return ResponseEntity.ok(vendorService.updateVendor(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable Integer id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.noContent().build(); // 204: No Content Status
    }
}
