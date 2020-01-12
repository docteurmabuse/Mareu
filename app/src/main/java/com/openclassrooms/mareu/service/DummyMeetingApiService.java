package com.openclassrooms.mareu.service;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.ui.meeting_list.filters.Filters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DummyMeetingApiService implements MeetingApiService {
    private List<Meeting> meetings = new ArrayList<>();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void resetMeetings() {
        meetings = new ArrayList<>();
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
    public List<Meeting> getFilteredMeetings(Date fDate, List<Filters.Places> fPlaces) {
        List<Meeting> fMeetings = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
        String mDate1 = null;
        if (fDate != null) {
            mDate1 = df.format(fDate);
        }
        if (fPlaces.size() > 0 && fDate == null) {
            for (Meeting meeting : getMeetings()) {
                for (Filters.Places places : fPlaces)
                    if (meeting.getmPlace().contains(places.getpName())) {
                        fMeetings.add(meeting);
                    }
            }
        } else if (fPlaces.size() > 0) {
            for (Meeting meeting : getMeetings()) {
                String mDate2 = df.format(meeting.getmDate());
                for (Filters.Places places : fPlaces) {
                    if (meeting.getmPlace().contains(places.getpName()) && mDate2.equals(mDate1)) {
                        fMeetings.add(meeting);
                    }
                }
            }
        } else if (fPlaces.size() < 1) {
            for (Meeting meeting : getMeetings()) {
                String mDate2 = df.format(meeting.getmDate());
                if (mDate2.equals(mDate1)) {
                    fMeetings.add(meeting);
                }
            }
        }
        return fMeetings;
    }
}