package com.openclassrooms.mareu.model;

import android.text.format.Time;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Meeting {

    /**
     * Meeting Identifier
     */
    private Integer mId;

    /**
     * Meeting Place
     */
    private String mAvatar;

    /**
     * Meeting Date
     */
    private Date mDate;

    /**
     * Meeting Time
     */
    private Time mTime;

    /**
     * Meeting Place
     */
    private String mPlace;

    /**
     * Meeting Subject
     */
    private String mSubject;

    /**
     * Meeting Partcipants List
     */
    private ArrayList<String> mParticipants;


    public Meeting(Integer mId, String mAvatar, Date mDate, Time mTime, String mPlace, String mSubject, ArrayList<String> mParticipants) {
        this.mId = mId;
        this.mAvatar = mAvatar;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mPlace = mPlace;
        this.mSubject = mSubject;
        this.mParticipants = mParticipants;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getmAvatar() {
        return mAvatar;
    }

    public void setmAvatar(String mAvatar) {
        this.mAvatar = mAvatar;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public Time getmTime() {
        return mTime;
    }

    public void setmTime(Time mTime) {
        this.mTime = mTime;
    }

    public String getmPlace() {
        return mPlace;
    }

    public void setmPlace(String mPlace) {
        this.mPlace = mPlace;
    }

    public String getmSubject() {
        return mSubject;
    }

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public ArrayList<String> getmParticipants() {
        return mParticipants;
    }

    public void setmParticipants(ArrayList<String> mParticipants) {
        this.mParticipants = mParticipants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meeting)) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(mId, meeting.mId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId);
    }
}
