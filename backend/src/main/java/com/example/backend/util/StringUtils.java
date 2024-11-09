package com.example.backend.util;

public class StringUtils {
    public static String camelToSnake(String camelCase) {
        // Use a regular expression to find uppercase letters and insert an underscore before them
        return camelCase.replaceAll("([a-z0-9])([A-Z])", "$1_$2").toLowerCase();
    }
}
