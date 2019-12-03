package com.example.mareu;


import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.service.FakeMeetingGenerator;
import com.openclassrooms.mareu.service.MeetingApiService;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class MeetingServiceTest {
    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getMeetingsWithSuccess() {
        Meeting meeting = new Meeting(1, 545121, "05/12/2019", "12:30", "salle Icare", "RéunionA", "laurent.tizzone@gmail.com,l.tizzone@gmail.com");
        //service.addMeeting(meeting);
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> dummyMeetingsExpected = FakeMeetingGenerator.FAKE_MEETING;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(dummyMeetingsExpected.toArray()));
    }

    @Test
    public void addMeetingWithSuccess() {
        Meeting meeting = new Meeting(1, 545121, "05/12/2019", "12:30", "salle Icare", "RéunionA", "laurent.tizzone@gmail.com,l.tizzone@gmail.com");
        service.addMeeting(meeting);
        assertEquals(1, service.getMeetings().size());
    }

    @Test
    public void deleteMeetingWithSuccess() {

    }
}
