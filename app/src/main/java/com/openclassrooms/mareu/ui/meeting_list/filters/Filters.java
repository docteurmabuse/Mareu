package com.openclassrooms.mareu.ui.meeting_list.filters;


import java.util.Date;
import java.util.List;

public class Filters {
    private Date date = null;
    private List<FiltersContent.Places> places = null;
    private String sortBy = null;

    public Filters(Date date, List<FiltersContent.Places> places, String sortBy) {
        this.date = date;
        this.places = places;
        this.sortBy = sortBy;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<FiltersContent.Places> getPlaces() {
        return places;
    }

    public void setPlaces(List<FiltersContent.Places> places) {
        this.places = places;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
