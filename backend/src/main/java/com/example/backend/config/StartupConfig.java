package com.example.backend.config;

import com.example.backend.service.StartupService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class StartupConfig {

    private final StartupService startupService;

    @PostConstruct
    public void init() {
        startupService.init();
    }
}
