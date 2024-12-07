package com.example.backend.shell.config;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RequiredArgsConstructor
@Component
public class ExitConfiguration {
    private static final Path LOG_FILE_PATH = Paths.get("custom_logs.log");

    @PreDestroy
    public void exit() throws IOException {
        System.out.printf(new AttributedString("[*] Cleaned Logs Cache", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW)).toAnsi());
        Files.deleteIfExists(LOG_FILE_PATH);
    }
}
