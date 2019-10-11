package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/YYYY");

    public static LocalDate of(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate date) {
        if (date == null) return "";
        return (date.equals(NOW)) ? "Сейчас" : date.format(DATE_FORMATTER);
    }

    public static LocalDate parse(String joinedString) {
        String[] arr = joinedString.split("/");
        if (joinedString.trim().isEmpty() || joinedString.equals("Сейчас") || arr.length != 2) return NOW;
        String monthStr = toNumbers(arr[0]);
        String yearStr = toNumbers(arr[1]);
        if (monthStr.isEmpty() || yearStr.isEmpty()) return NOW;
        int month = Integer.parseInt(monthStr);
        int year = Integer.parseInt(yearStr);
        if (month < 1 || month > 12 || yearStr.length() > 4) return NOW;
        return LocalDate.of(year, month, 1);
    }

    private static String toNumbers(String s) {
        return s.replaceAll("[^0-9]", "");
    }
}