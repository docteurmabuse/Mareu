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
 * TODO: Replace all uses of this class before publishing your app.
 */
public class FilterContent {

    /**
     * An array of Filters List items.
     */
    public static final List<FiltersItem> ITEMS = Arrays.asList(
            new FiltersItem(1, "Lieu"),
            new FiltersItem(1, "Date")
    );

    /**
     * An array of Meetings Place List items.
     */
    public static final List<Places> PLACE_ITEMS = Arrays.asList(
            new Places(1, "Lieu"),
            new Places(1, "Date")
    );
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Integer, FiltersItem> ITEM_MAP = new HashMap<Integer, FiltersItem>();


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
        final String fPlaces;

        public Places(Integer id, String fPlaces) {
            this.id = id;
            this.fPlaces = fPlaces;
        }

        public Integer getId() {
            return id;
        }

        public String getfPlaces() {
            return fPlaces;
        }
    }
}
