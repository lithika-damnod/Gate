package com.example.backend.service;

import com.example.backend.config.GlobalConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

@RequiredArgsConstructor
@Service
public class StartupService {

    private final GlobalConfig globalConfig;

    // ANSI escape codes for colors
    final String RESET = "\033[0m";       // Reset color
    final String GREEN = "\033[0;32m";    // Green text
    final String CYAN = "\033[0;36m";     // Cyan text
    final String BOLD = "\033[1m";        // Bold text
    final String DIM = "\033[2m";
    final String LIGHT_GRAY = "\033[0;37m";
    final String UNDERLINE = "\033[4m";

    final String logFilePath = Paths.get("custom_logs.log").toAbsolutePath().toString();
    final String initFilePath = Paths.get("init.json").toAbsolutePath().toString();

    public void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Saving server logs to: " + CYAN + logFilePath + RESET);
        System.out.println("Populating server with data from: " + CYAN + initFilePath + RESET);
        System.out.println();
        System.out.print("Enter the " + BOLD + "user retrieval rate" + RESET + " for this session " + LIGHT_GRAY + "[default: 12000ms]" + RESET + "\n> ");
        String input = scanner.nextLine();
        if(!input.isEmpty()) {
            globalConfig.setUserRetrievalRate(Integer.parseInt(input));
        }
        System.out.println();

        // Example server details
        String ipAddress = "127.0.0.1";
        int port = 8080;
        String status = "Running";
        String version = "v1.0.0";
        String environment = "Development";

        System.out.println("Server Information:");
        System.out.println("- IP Address   : " + ipAddress);
        System.out.println("- Port         : " + port);
        System.out.println("- Environment  : " + environment);
        System.out.println("- Status       : " + GREEN + status + RESET);
        System.out.println("- Version      : " + version);
        System.out.println();

        // Prompt user to enter a command
        System.out.println("\nType 'help' for more options.\n");
    }
}
