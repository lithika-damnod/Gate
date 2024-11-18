package com.example.backend.repository;

import com.example.backend.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.rmi.server.UID;

public interface TicketRepository
        extends JpaRepository<Ticket, UID> { }
