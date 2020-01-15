package com.openclassrooms.mareu.model;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class Place {
    /**
     * An array of Meetings Place List items.
     */
    public static final List<Place> FAKE_PLACES = Arrays.asList(
            new Place(1, "Birdo"),
            new Place(2, "Daisy"),
            new Place(3, "Donkey Kong"),
            new Place(4, "Harmonie"),
            new Place(5, "Luigi"),
            new Place(6, "Mario"),
            new Place(7, "Peach"),
            new Place(8, "Toad"),
            new Place(9, "Wario"),
            new Place(10, "Yoshi")
    );

    private final Integer id;

    @NonNull
    private final String name;

    private Place(Integer id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @NonNull
    public static Place getPlaceById(int id) {
        for (Place place : getAllPlaces()) {
            if (place.id == id)
                return place;
        }
        return null;
    }

    @NonNull
    public static Place[] getAllPlaces() {
        return new Place[]{
                new Place(1, "Birdo"),
                new Place(2, "Daisy"),
                new Place(3, "Donkey Kong"),
                new Place(4, "Harmonie"),
                new Place(5, "Luigi"),
                new Place(6, "Mario"),
                new Place(7, "Peach"),
                new Place(8, "Toad"),
                new Place(9, "Wario"),
                new Place(10, "Yoshi"),
        };
    }

    @Override
    @NonNull
    public String toString() {
        return getName();
    }
}
