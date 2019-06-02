package ru.javawebinar.basejava;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainData {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Date date = new Date();
        System.out.println(date);
        System.out.println(System.currentTimeMillis());  //from 1970.01.01
        System.out.println(System.currentTimeMillis() - start);
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("America/Honolulu"));
        System.out.println(cal.getTime());

        LocalDate ld = LocalDate.now();
        System.out.println(ld);
        LocalTime lt = LocalTime.now();
        System.out.println(lt);
        LocalDateTime ldt = LocalDateTime.of(ld, lt);
        System.out.println(ldt);

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM");
        System.out.println(sdf.format(date));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/YYYY");
        System.out.println(dtf.format(ldt));
    }
}
