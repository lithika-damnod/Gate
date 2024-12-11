package com.example.backend.shell.commands;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

@ShellComponent
public class LogCommands {

    private static final Path LOG_FILE_PATH = Paths.get("custom_logs.log");
    private static volatile boolean isLogViewerActive = false;

    public static void logMessage(String message) {
        String logEntry = message;
        try {
            saveLogToFile(logEntry);
            if(isLogViewerActive) {
                System.out.println(setLogMessageColor(logEntry));
            }
        } catch(IOException e) {
            System.out.println(new AttributedString("Failed to log message" + e.getMessage(), AttributedStyle.DEFAULT.foreground(AttributedStyle.RED)).toAnsi());
        }
    }

    private static void saveLogToFile(String logEntry) throws IOException {
        if (LOG_FILE_PATH.getParent() != null && !Files.exists(LOG_FILE_PATH.getParent())) {
            Files.createDirectories(LOG_FILE_PATH.getParent());
        }

        if (!Files.exists(LOG_FILE_PATH)) {
            Files.createFile(LOG_FILE_PATH);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH.toFile(), true))) {
            writer.write(logEntry);
            writer.newLine();
        }
    }

    private static String setLogMessageColor(String log) {
        if (log.contains("BUY")) {
            return new AttributedString(log, AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN)).toAnsi();
        } else if (log.contains("RELEASE")) {
            return new AttributedString(log, AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW)).toAnsi();
        } else {
            return new AttributedString(log, AttributedStyle.DEFAULT.foreground(AttributedStyle.RED)).toAnsi();
        }
    }


    @ShellMethod(key="log", value="View System Logs")
    public void log() {
        isLogViewerActive = true;
        System.out.println(new AttributedString("Log viewer started. Press Ctrl+C to exit.", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW)).toAnsi());

        try {
            // create the file if it doesn't exist
            if (!Files.exists(LOG_FILE_PATH)) {
                Files.createFile(LOG_FILE_PATH);
            }

            Scanner scanner = new Scanner(LOG_FILE_PATH.toFile());
            while(isLogViewerActive) {
                while(scanner.hasNextLine()) {
                    System.out.println(setLogMessageColor(scanner.nextLine()));
                }
                Thread.sleep(500);
            }
        }
        catch (IOException e) {
            System.out.println(new AttributedString("Error reading log file: " + e.getMessage(), AttributedStyle.DEFAULT.foreground(AttributedStyle.RED)).toAnsi());
        } catch(InterruptedException e) {
            System.out.println(new AttributedString("Log viewer interrupted.", AttributedStyle.DEFAULT.foreground(AttributedStyle.RED)).toAnsi());
        } finally {
            isLogViewerActive = false;
        }
    }
}
