package com.example.backend.repository;

import com.example.backend.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository
        extends JpaRepository<Address, Integer> {
    Optional<Address> findByAddress(String address);
}

