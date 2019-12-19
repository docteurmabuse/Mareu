package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FakeMeetingGenerator {
    public static List<Meeting> FAKE_MEETING = Arrays.asList(
            new Meeting(1, 0xFF5E855F, new Date(), "12h30", "Mario", "Réunion A", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
            new Meeting(2, 0xFF5E755F, new Date(), "12h30", "Mario", "Réunion A", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
            new Meeting(3, 0xFF5E888F, new Date(), "12h30", "Wario", "Réunion A", "laurent.tizzone@gmail.com,l.tizzone@gmail.com")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(FAKE_MEETING);
    }
}
