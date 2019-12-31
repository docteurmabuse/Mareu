package com.openclassrooms.mareu.ui.meeting_list;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.openclassrooms.mareu.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MeetingsListTest {

    //This is fix
    private static int ITEM_COUNT = 6;

    @Rule
    public ActivityTestRule<MeetingsActivity> mActivityTestRule = new ActivityTestRule<>(MeetingsActivity.class);

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
        //onView(ViewMatchers.withId(R.id.meetings_recylerview)).check(withItemCount)
        // When : We perform click on add meeting button

        // Then : The number of Element is 7
        ViewInteraction imageButton = onView(
                allOf(withId(R.id.fab_add_meeting),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));
    }

    @Test
    public void checkIfDeleteMeetingIsWorking() {
        // Given : We remove the element at position 2

        // When perform a click on a delete icon

        //Then then number of element is 5
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.item_list_meeting_delete_button), withContentDescription("Delete Meeting Button"),
                        childAtPosition(
                                allOf(withId(R.id.constraint),
                                        childAtPosition(
                                                withId(R.id.item_list_meeting_avatar),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());
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
}
