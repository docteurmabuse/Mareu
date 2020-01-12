package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FakeMeetingGenerator {
    private static Date currentDate = new Date(System.currentTimeMillis());
    private static Date tomorrow = new Date(System.currentTimeMillis() + 86400000);

    public static List<Meeting> FAKE_MEETINGS = Arrays.asList(
            new Meeting(1, 0xFF5E855F, currentDate, "Mario", "Réunion A", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
            new Meeting(2, 0xFF5E755F, tomorrow, "Mario", "Réunion B", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
            new Meeting(3, 0xFF5E888F, currentDate, "Wario", "Réunion C", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
            new Meeting(4, 0xFF5E155F, tomorrow, "Wario", "Réunion D", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
            new Meeting(5, 0xFF5E668F, currentDate, "Birdo", "Réunion E", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
            new Meeting(6, 0xFF5E338F, currentDate, "Yoshi", "Réunion F", "laurent.tizzone@gmail.com,l.tizzone@gmail.com")
    );

    public FakeMeetingGenerator() throws ParseException {
    }

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(FAKE_MEETINGS);
    }
}
