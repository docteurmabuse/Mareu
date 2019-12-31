package com.openclassrooms.mareu.ui.meeting_list.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class Filters {

    /**
     * An array of Meetings Place List items.
     */
    public static final List<Places> PLACE_ITEMS = Arrays.asList(
            new Places(1, "Mario"),
            new Places(2, "Luigi"),
            new Places(3, "Peach"),
            new Places(4, "Toad"),
            new Places(5, "Yoshi"),
            new Places(6, "Daisy"),
            new Places(7, "Harmonie"),
            new Places(8, "Donkey Kong"),
            new Places(10, "Birdo")
    );

    public static List<Places> createPlaces() {
        return new ArrayList<>(PLACE_ITEMS);
    }

    public static class Places {
        public final Integer id;
        final String pName;

        public Places(Integer id, String pName) {
            this.id = id;
            this.pName = pName;
        }

        public Integer getId() {
            return id;
        }

        public String getpName() {
            return pName;
        }

    }
}
