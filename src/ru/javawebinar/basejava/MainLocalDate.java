package ru.javawebinar.basejava;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainLocalDate {
    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2015, 12, 01);
        System.out.println(date);
        String inputDate = "2015-12-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/YYYY");
        date = LocalDate.parse(inputDate);
        System.out.println(date);
        System.out.println(date.format(formatter));

        String joinedString = "01/2014";
        String[] arr = joinedString.split("/");
        int month = Integer.parseInt(arr[0]);
        int year = Integer.parseInt(arr[1]);
        System.out.println("result: " +LocalDate.of(year, month, 1));
    }
}
