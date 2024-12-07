package com.example.backend.shell.service;

import com.example.backend.models.Vendor;
import com.example.backend.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ShellVendorService {

    private final VendorRepository vendorRepository;

    public void get(Integer id) {
        if (id == null) {
            List<Vendor> vendors = vendorRepository.findAll();

            int[] maxWidths = new int[3];
            for (Vendor vendor : vendors) {
                maxWidths[0] = Math.max(maxWidths[0], vendor.getId().toString().length());
                maxWidths[1] = Math.max(maxWidths[1], vendor.getName().length());
                maxWidths[2] = Math.max(maxWidths[2], vendor.getAddress().length());
            }

            String[] headers = {
                    "ID",
                    "Vendor Name",
                    "Address"
            };

            String header = String.format("%-" + Math.max(1, maxWidths[0]) + "s | %-" + Math.max(1, maxWidths[1]) + "s | %-" + Math.max(1, maxWidths[2]) + "s\n", (Object[]) headers);
            System.out.print(header);
            System.out.println("-" + "-".repeat(header.length()));
            for (Vendor vendor : vendors) {
                System.out.printf("%-" + (Math.max(1, maxWidths[0]) + 1) + "s | %-" + (Math.max(1, maxWidths[1])) + "s | %-" + (Math.max(1, maxWidths[2]) + 1) + "s\n", vendor.getId(), vendor.getName(), vendor.getAddress());
            }
        } else {
            Vendor vendor = vendorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entered Id is not valid!"));
            System.out.print(new AttributedString("Id: ", AttributedStyle.BOLD).toAnsi() + vendor.getId() + "\n" + new AttributedString("Vendor Name: ", AttributedStyle.BOLD).toAnsi() + vendor.getName() + "\n" + new AttributedString("Address: ", AttributedStyle.BOLD).toAnsi() + vendor.getAddress() + "\n");
        }
    }
}