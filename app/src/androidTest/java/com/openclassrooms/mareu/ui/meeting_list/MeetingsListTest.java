package com.openclassrooms.mareu.ui.meeting_list;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
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

    //This is fix
    private static int ITEMS_COUNT = 6;

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
        onView(ViewMatchers.withId(R.id.meetings_recylerview)).check(withItemCount(ITEMS_COUNT));
        // When : We perform click on add meeting button
        //onView(ViewMatchers.withId(R.id.fab_add_meeting)).perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab_add_meeting),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

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

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.date_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.date_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.time_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.time_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.participants_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.participants_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("Laurent@free.Fr,Roger@orange.Fr"), closeSoftKeyboard());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.participants_input), withText("Laurent@free.Fr,Roger@orange.Fr"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.participants_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(pressImeActionButton());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.form_btn), withText("Créer la réunion"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton.perform(click());

        // Then : The number of Element is 7
        onView(ViewMatchers.withId(R.id.meetings_recylerview)).check(withItemCount(ITEMS_COUNT + 1));
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
}
