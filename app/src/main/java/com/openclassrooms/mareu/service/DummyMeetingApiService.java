package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.ui.meeting_list.util.FiltersContent;

import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public List<Meeting> getPlaceFilteredMeetings(Date fDate, List<FiltersContent.Places> fPlaces) {
        List<Meeting> fMeetings = new ArrayList<>();
        for (Meeting meeting : getMeetings()) {
            for (FiltersContent.Places places : fPlaces) {
                if (meeting.getmPlace().contains(places.getpName())) {
                    fMeetings.add(meeting);
                }
            }
        }
        return fMeetings;
    }


}
