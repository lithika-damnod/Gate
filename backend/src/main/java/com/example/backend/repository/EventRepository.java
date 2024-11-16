package com.example.backend.repository;

import com.example.backend.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository
        extends JpaRepository<Event, Integer> { }
