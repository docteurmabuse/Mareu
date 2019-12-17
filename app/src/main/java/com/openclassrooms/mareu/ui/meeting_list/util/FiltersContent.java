package com.openclassrooms.mareu.ui.meeting_list.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class FiltersContent {

    /**
     * An array of FiltersContent List items.
     */
    public static final List<FiltersItem> ITEMS = Arrays.asList(
            new FiltersItem(1, "Date")
    );

    /**
     * An array of Meetings Place List items.
     */
    public static final List<Places> PLACE_ITEMS = Arrays.asList(
            new Places(1, "Mario", true),
            new Places(2, "Luigi", true),
            new Places(3, "Peach", true),
            new Places(4, "Toad", true),
            new Places(5, "Yoshi", true),
            new Places(6, "Daisy", true),
            new Places(7, "Harmonie", true),
            new Places(8, "Donkey Kong", true),
            new Places(9, "Wario", true),
            new Places(10, "Birdogi", true)
    );
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Integer, FiltersItem> ITEM_MAP = new HashMap<Integer, FiltersItem>();
    public static final Map<Integer, Places> ITEM_PLACE_MAP = new HashMap<>();


    public static List<FiltersItem> createDummyItem() {
        return new ArrayList<>(ITEMS);
    }

    public static List<Places> createPlaces() {
        return new ArrayList<>(PLACE_ITEMS);
    }


    public static class FiltersItem {
        public final Integer id;
        final String fName;

        public FiltersItem(Integer id, String name) {
            this.id = id;
            this.fName = name;
        }

        @Override
        public String toString() {
            return fName;
        }
    }

    public static class Places {
        public final Integer id;
        final String pName;
        final Boolean isSelected;


        public Places(Integer id, String pName, Boolean isSelected) {
            this.id = id;
            this.pName = pName;
            this.isSelected = isSelected;
        }

        public Integer getId() {
            return id;
        }

        public String getpName() {
            return pName;
        }

        public Boolean getSelected() {
            return isSelected;
        }

    }
}
