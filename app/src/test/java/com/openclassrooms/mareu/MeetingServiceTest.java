package com.openclassrooms.mareu;


import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.Place;
import com.openclassrooms.mareu.service.MeetingApiService;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.openclassrooms.mareu.service.FakeMeetingGenerator.FAKE_MEETINGS;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Unit test on Meeting service.
 */
@RunWith(JUnit4.class)
public class MeetingServiceTest {
    private MeetingApiService service;
    private static Date currentDate = new Date(System.currentTimeMillis());
    private static Date tomorrow = new Date(System.currentTimeMillis() + 86400000);

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }


    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        //List should be empty at start
        assertEquals(0, service.getMeetings().size());
        //add Dummy to the meeting list
        service.getMeetings().addAll(FAKE_MEETINGS);
        List<Meeting> dummyMeetingsExpected = FAKE_MEETINGS;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(dummyMeetingsExpected.toArray()));
    }

    @Test
    public void addMeetingWithSuccess() {
        Meeting meetingToAdd = FAKE_MEETINGS.get(0);
        service.addMeeting(meetingToAdd);
        assertEquals(1, service.getMeetings().size());
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = FAKE_MEETINGS.get(0);
        service.getMeetings().clear();
        service.getMeetings().add(meetingToDelete);
        assertEquals(1, service.getMeetings().size());
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
        assertEquals(0, service.getMeetings().size());


    }

    @Test
    public void getFilteredMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        //List should be empty at start
        assertEquals(0, service.getMeetings().size());
        //add Dummy to the meeting lisr
        service.getMeetings().addAll(FAKE_MEETINGS);
        List<Place> placeSelected = Arrays.asList(
                new Place(9, "Wario"),
                new Place(1, "Mario")
        );

        List<Meeting> meetingsDatePlaces = service.getFilteredMeetings(tomorrow, placeSelected);
        List<Meeting> fMeetingExpectedDatePlaces = Arrays.asList(
                new Meeting(2, 0xFF5E755F, tomorrow, "Mario", "Réunion B", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
                new Meeting(4, 0xFF5E155F, tomorrow, "Wario", "Réunion D", "laurent.tizzone@gmail.com,l.tizzone@gmail.com")
        );
        assertArrayEquals(meetingsDatePlaces.toArray(), fMeetingExpectedDatePlaces.toArray());

        List<Meeting> meetingsEmptyDate = service.getFilteredMeetings(null, placeSelected);
        List<Meeting> fMeetingExpectedNoDate = Arrays.asList(
                new Meeting(1, 0xFF5E855F, currentDate, "Mario", "Réunion A", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
                new Meeting(2, 0xFF5E755F, tomorrow, "Mario", "Réunion B", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
                new Meeting(3, 0xFF5E888F, currentDate, "Wario", "Réunion C", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
                new Meeting(4, 0xFF5E155F, tomorrow, "Wario", "Réunion D", "laurent.tizzone@gmail.com,l.tizzone@gmail.com")
        );
        assertArrayEquals(meetingsEmptyDate.toArray(), fMeetingExpectedNoDate.toArray());

        List<Place> emptyPlaceSelected = new ArrayList<>();
        List<Meeting> meetingsEmptyPlaces = service.getFilteredMeetings(currentDate, emptyPlaceSelected);
        List<Meeting> fMeetingExpectedDateNoPlaces = Arrays.asList(
                new Meeting(1, 0xFF5E855F, currentDate, "Mario", "Réunion A", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
                new Meeting(3, 0xFF5E888F, currentDate, "Wario", "Réunion C", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
                new Meeting(5, 0xFF5E668F, currentDate, "Birdo", "Réunion E", "laurent.tizzone@gmail.com,l.tizzone@gmail.com"),
                new Meeting(6, 0xFF5E338F, currentDate, "Yoshi", "Réunion F", "laurent.tizzone@gmail.com,l.tizzone@gmail.com")
        );
        assertArrayEquals(meetingsEmptyPlaces.toArray(), fMeetingExpectedDateNoPlaces.toArray());
    }

    @Test
    public void resetMeetingListWithSuccess() {
        //Add fake meetings to the list
        service.getMeetings().addAll(FAKE_MEETINGS);
        //Meeting list size list should be 6
        assertEquals(6, service.getMeetings().size());
        //Reset meeting list
        service.resetMeetings();
        assertEquals(0, service.getMeetings().size());

    }
}
