package com.openclassrooms.mareu.model;

import java.util.Objects;

public class Meeting {

    /**
     * Meeting Identifier
     */
    private Integer mId;

    /**
     * Meeting Place
     */
    private Integer mAvatar;

    /**
     * Meeting Date
     */
    private String mDate;

    /**
     * Meeting Time
     */
    private String mTime;

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
    private String mParticipants;


    public Meeting(Integer mId, Integer mAvatar, String mDate, String mTime, String mPlace, String mSubject, String mParticipants) {
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

    public Integer getmAvatar() {
        return mAvatar;
    }

    public void setmAvatar(Integer mAvatar) {
        this.mAvatar = mAvatar;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
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

    public String getmParticipants() {
        return mParticipants;
    }

    public void setmParticipants(String mParticipants) {
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
