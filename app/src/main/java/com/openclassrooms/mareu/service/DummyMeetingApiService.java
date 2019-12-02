package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;

import java.util.List;

import static com.openclassrooms.mareu.service.FakeMeetingGenerator.generateMeetings;

public class DummyMeetingApiService implements MeetingApiService {
    public List<Meeting> meetings = generateMeetings();


    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    /**
     * add a {@Link Meeting} to the meeting list
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
