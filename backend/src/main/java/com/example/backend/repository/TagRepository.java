package com.example.backend.repository;

import com.example.backend.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository
        extends JpaRepository<Tag, String> { }