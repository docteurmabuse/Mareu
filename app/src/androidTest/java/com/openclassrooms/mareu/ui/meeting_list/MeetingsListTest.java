package com.openclassrooms.mareu.ui.meeting_list;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.openclassrooms.mareu.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.TimeZone;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MeetingsListTest {

    private static int ITEMS_COUNT = 6;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minutes;


    @Rule
    public ActivityTestRule<MeetingsActivity> mActivityTestRule = new ActivityTestRule<>(MeetingsActivity.class);

    @Before
    public void setUp() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        //getTime() returns the current date in default time zone
        day = calendar.get(Calendar.DATE);
        //Note: +1 the month for current month
        month = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }


    @Test
    public void checkIfAddingMeetingIsWorking() {
        // Given :  We add one element
        onView(ViewMatchers.withId(R.id.meetings_recylerview)).check(withItemCount(ITEMS_COUNT));
        // When : We perform click on add meeting button
        onView(ViewMatchers.withId(R.id.fab_add_meeting)).perform(click());
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.name_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.subject),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Réunion G"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.place_spinner), withContentDescription("Lieu"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(4);
        appCompatTextView.perform(click());

        // Show the date picker by typing twice on date  input text
        onView(withId(R.id.date_input)).perform(click());
        onView(withId(R.id.date_input)).perform(click());
        // Sets a date on the date picker widget
        // onView(isAssignableFrom(DatePicker.class)).perform(setDate(year, month, day+1));
        // Confirm the selected date for today.
        onView(withId(android.R.id.button1)).perform(click());
        // Check if the selected date is correct and is displayed in the Ui.
        onView(withId(R.id.date_input)).check(matches(allOf(withText(String.format("%02d/%02d", day, month) + '/' + year),
                isDisplayed())));
        // Show the time picker by typing twice on time input text
        onView(withId(R.id.time_input)).perform(click());
        onView(withId(R.id.time_input)).perform(click());
        // Sets a time on the time picker widget
        onView(isAssignableFrom(TimePicker.class)).perform(setTime(hour + 1, minutes));
        // Confirm the selected time.
        onView(withId(android.R.id.button1)).perform(click());
        // Check if the selected time is correct and is displayed in the Ui.
        onView(withId(R.id.time_input)).check(matches(allOf(withText(String.format("%02dh%02d", hour + 1, minutes)),
                isDisplayed())));
        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.participants_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.participants_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("laurent@free.fr,Roger@orange.fr"), closeSoftKeyboard());
        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.participants_input), withText("Laurent@free.fr,Roger@orange.fr"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.participants_layout),
                                        0),
                                0),
                        isDisplayed()));
        onView(withId(R.id.form_btn)).perform(click());

        // Then : The number of Element is 7
        onView(ViewMatchers.withId(R.id.meetings_recylerview)).check(withItemCount(ITEMS_COUNT + 1));
    }

    @Test
    public void checkIfFormValidationIsWorking_NewMeetingView() {
        onView(ViewMatchers.withId(R.id.fab_add_meeting)).perform(click());
        onView(withId(R.id.form_btn)).perform(click());
        onView(ViewMatchers.withId(R.id.subject)).check(matches(withText("Ce champ est requis!")));

    }

    @Test
    public void checkIfDeleteMeetingIsWorking() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.meetings_recylerview)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.item_list_meeting_delete_button)).perform(click());
        //Then then number of element is 5
        onView(ViewMatchers.withId(R.id.meetings_recylerview)).check(withItemCount(ITEMS_COUNT - 1));
    }

    @Test
    public void checkIfLaunchingDetailMeetingIsWorking() {
        ViewInteraction materialCardView = onView(
                allOf(withId(R.id.item_list_meeting_avatar),
                        childAtPosition(
                                allOf(withId(R.id.meetings_recylerview),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                1)),
                                1),
                        isDisplayed()));
        materialCardView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.place_detail), withText("Mario"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.item_detail_container),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Mario")));

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.toolbar_layout), withContentDescription("Réunion B"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        0),
                                0),
                        isDisplayed()));
        frameLayout.check(matches(isDisplayed()));
    }

    @Test
    public void checkIfFiltersAreWorking() {
        //Click on filter menu
        onView(withId(R.id.icon_filter_menu)).perform(click());
        //Click on Date Btn
        onView(withId(R.id.date_btn)).perform(click());
        // Sets a date in 3 days from now on the date picker widget
        onView(isAssignableFrom(DatePicker.class)).perform(setDate(year, month, day + 3));
        // Confirm the selected date.
        onView(withId(android.R.id.button1)).perform(click());
        // Check if the selected date is correct and is displayed in the Ui.
        onView(withId(R.id.filter_date_txt)).check(matches(allOf(withText(String.format("%02d/%02d", day + 3, month) + '/' + year),
                isDisplayed())));
        //Click on filter button
        onView(withId(R.id.finnish_btn)).perform(click());
        //Check if the meeting list is Empty
        // Then : The number of Element is 7
        onView(ViewMatchers.withId(R.id.meetings_recylerview)).check(withItemCount(0));
        //Reset filters by clicking on Rest filter button
        onView(withId(R.id.reset_filer_btn)).perform(click());
        // Then : The number of Element is 7
        onView(ViewMatchers.withId(R.id.meetings_recylerview)).check(withItemCount(ITEMS_COUNT));
    }
}
