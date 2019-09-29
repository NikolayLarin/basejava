package ru.javawebinar.basejava.util;

import java.time.LocalDate;

public class DateUtil {

    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, int month) {
        return LocalDate.of(year, month, 1);
    }
    public static LocalDate of(String joinedString) {
        String[] arr = joinedString.split("/");
        int month = Integer.parseInt(arr[0]);
        int year = Integer.parseInt(arr[1]);
        return LocalDate.of(year, month, 1);
    }
}
