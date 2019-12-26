package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FakeMeetingGenerator {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH);

    public static String date2 = new Date().toString();

    public static List<Meeting> FAKE_MEETING = Arrays.asList(
            new Meeting(1, 0xFF5E855F, parseDate(), "12:30", "Mario", "Réunion A", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
            new Meeting(2, 0xFF5E755F, parseDate(), "12:30", "Mario", "Réunion B", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
            new Meeting(3, 0xFF5E888F, parseDate(), "12:30", "Wario", "Réunion C", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
            new Meeting(4, 0xFF5E155F, parseDate(), "12:30", "Wario", "Réunion D", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
            new Meeting(5, 0xFF5E668F, parseDate(), "10:30", "Birdo", "Réunion E", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
            new Meeting(6, 0xFF5E338F, parseDate(), "11:30", "Yoshi", "Réunion F", "laurent.tizzone@gmail.com,l.tizzone@gmail.com")

    );

    public FakeMeetingGenerator() throws ParseException {
    }

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(FAKE_MEETING);
    }

    public static Date parseDate() {
        Date date = new Date();
       // Date date2 = new Date();
        DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT, new Locale("FR","fr"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
          Date  date2 = simpleDateFormat.parse(mediumDateFormat.format(date));
            return date2;
        } catch (ParseException e) {
            System.out.println("ParseError " + e.getMessage());
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
    }

}
