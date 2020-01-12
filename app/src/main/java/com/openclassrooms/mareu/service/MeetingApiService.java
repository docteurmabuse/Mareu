package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.ui.meeting_list.filters.Filters;

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
     * Reset a meeting List
     */

    void resetMeetings();

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

    List<Meeting> getFilteredMeetings(Date fDate, List<Filters.Places> fPlaces);

}

