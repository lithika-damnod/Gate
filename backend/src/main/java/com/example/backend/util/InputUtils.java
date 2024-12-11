package com.example.backend.util;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@RequiredArgsConstructor
public class InputUtils {

   private static final Scanner scanner = new Scanner(System.in);

    public static String inputString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static Integer inputInteger(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    public static LocalDateTime inputDateTime(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        while(true) {
            System.out.print(prompt);
            String time = scanner.nextLine();
            try {
                return LocalDateTime.parse(time, formatter);
            }
            catch(Exception e) {
                System.out.println("Invalid date and time format. Please use 'yyyy/MM/dd HH:mm'.");
            }
        }
    }
}
