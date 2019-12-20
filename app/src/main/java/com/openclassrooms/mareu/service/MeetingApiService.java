package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.ui.meeting_list.util.FiltersContent;

import java.util.Date;
import java.util.List;

/**
 * Meeting Api Service
 */
public interface MeetingApiService {

    /**
     * @return {@Link List}
     */
    List<Meeting> getMeetings();

    /**
     * Add a meeting
     *
     * @param meeting
     */
    void addMeeting(Meeting meeting);

    /**
     * Delete a meeting
     *
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    List<Meeting> getPlaceFilteredMeetings(Date fDate, List<FiltersContent.Places> fPlaces);
}

