package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FakeMeetingGenerator {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH);
    public static Date date = new Date();
    public static List<Meeting> FAKE_MEETING;

    static {
        try {
            FAKE_MEETING = Arrays.asList(
                    new Meeting(1, 0xFF5E855F, dateFormat.parse(date.toString()), "12:30", "Mario", "Réunion A", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
                    new Meeting(2, 0xFF5E755F, dateFormat.parse(date.toString()), "12:30", "Mario", "Réunion B", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
                    new Meeting(3, 0xFF5E888F, dateFormat.parse(date.toString()), "12:30", "Wario", "Réunion C", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
                    new Meeting(4, 0xFF5E155F, dateFormat.parse(date.toString()), "12:30", "Wario", "Réunion D", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
                    new Meeting(5, 0xFF5E668F, dateFormat.parse(date.toString()), "10:30", "Birdo", "Réunion E", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
                    new Meeting(6, 0xFF5E338F, dateFormat.parse(date.toString()), "11:30", "Yoshi", "Réunion F", "laurent.tizzone@gmail.com,l.tizzone@gmail.com")

            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(FAKE_MEETING);
    }
}
