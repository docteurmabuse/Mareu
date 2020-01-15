package com.openclassrooms.mareu.model;

import java.util.ArrayList;
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
    public static final List<Place> PLACE_ITEMS = Arrays.asList(
            new Place(1, "Mario"),
            new Place(2, "Luigi"),
            new Place(3, "Peach"),
            new Place(4, "Toad"),
            new Place(5, "Yoshi"),
            new Place(6, "Daisy"),
            new Place(7, "Harmonie"),
            new Place(8, "Donkey Kong"),
            new Place(10, "Birdo")
    );
    public final Integer id;
    final String name;

    public Place(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Place> createPlaces() {
        return new ArrayList<>(PLACE_ITEMS);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
