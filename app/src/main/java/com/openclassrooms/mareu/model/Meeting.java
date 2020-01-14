package com.openclassrooms.mareu.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Meeting implements Serializable {

    /**
     * Meeting Identifier
     */
    private Integer id;

    /**
     * Meeting Place
     */
    private Integer avatar;

    /**
     * Meeting Date
     */
    private Date date;


    /**
     * Meeting Place
     */
    private String place;

    /**
     * Meeting Subject
     */
    private String subject;

    /**
     * Meeting Partcipants List
     */
    private String participants;


    public Meeting(Integer id, Integer avatar, Date date, String place, String subject, String participants) {
        this.id = id;
        this.avatar = avatar;
        this.date = date;
        this.place = place;
        this.subject = subject;
        this.participants = participants;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meeting)) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(id, meeting.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
