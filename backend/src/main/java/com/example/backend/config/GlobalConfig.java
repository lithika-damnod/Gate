package com.example.backend.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app.global")
@Configuration
public class GlobalConfig {
    private Integer userRetrievalRate = 120000; // two minutes
}
