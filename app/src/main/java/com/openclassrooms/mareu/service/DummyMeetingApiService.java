package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {
    public static final List<Meeting> meetings = new ArrayList<Meeting>();


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
