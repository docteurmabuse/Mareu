package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {
    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
    );
    private static List<Meeting> meetings = generateNeighbours();

    static List<Meeting> generateNeighbours() {
        return new ArrayList<>(DUMMY_MEETING);
    }

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    /**
     * add a {@Link Meeting} to the meting list
     *
     * @param meeting
     */
    @Override
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }


    /**
     * remove a {@Link Meeting} to the meting list
     *
     * @param meeting
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }
}
