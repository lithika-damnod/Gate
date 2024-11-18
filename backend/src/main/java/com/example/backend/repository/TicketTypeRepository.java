package com.example.backend.repository;

import com.example.backend.models.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketTypeRepository
        extends JpaRepository<TicketType, Integer> {}