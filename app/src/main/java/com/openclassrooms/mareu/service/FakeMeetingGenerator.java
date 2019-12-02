package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeMeetingGenerator {
    public static List<Meeting> FAKE_MEETING = Arrays.asList(
            new Meeting(1, 545121, "05/12/2019", "12:30", "salle Icare", "RéunionA", "laurent.tizzone@gmail.com,l.tizzone@gmail.com")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(FAKE_MEETING);
    }
}
