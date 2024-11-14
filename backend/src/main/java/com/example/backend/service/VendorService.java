package com.example.backend.service;

import com.example.backend.dto.VendorRequest;
import com.example.backend.models.Vendor;
import com.example.backend.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorService {
    private final VendorRepository vendorRepository;

    public List<Vendor> getVendor() {
        return vendorRepository.findAll();
    }

    public Vendor getVendor(Integer id) {

        Vendor vendor = vendorRepository.findById(id).isPresent() ? vendorRepository.findById(id).get() : null;
        if(vendor == null) {
            throw new EntityNotFoundException("Vendor Not Found");
        }

        return vendor;
    }

    public Vendor registerVendor(VendorRequest request) {
        return vendorRepository.save(request.map());
    }

    public Vendor updateVendor(Integer id, VendorRequest request) {

        if(request.getName() == null && request.getAddress() == null) throw new IllegalArgumentException("Invalid Request");

        Vendor vendor = vendorRepository.findById(id).isPresent() ? vendorRepository.findById(id).get() : null;
        if(vendor == null) throw new EntityNotFoundException("Vendor Not Found");
        vendor.update(request.map().getName(), request.map().getAddress());

        return vendorRepository.save(vendor);
    }

    public void deleteVendor(Integer id) {
        Vendor vendor = vendorRepository.findById(id).isPresent() ? vendorRepository.findById(id).get() : null;
        if(vendor == null) throw new EntityNotFoundException("Vendor Not Found");

        vendorRepository.delete(vendor);
    }
}
