package uk.co.botondbutuza.currency;

import android.support.test.espresso.contrib.PickerActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.DatePicker;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.co.botondbutuza.currency.ui.main.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by brotond on 03/11/2017.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void noFromSelected() {
        onView(withText("Chart")).perform(click());

        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.no_start_date))).check(matches(isDisplayed()));
    }

    @Test
    public void selectDateFrom() {
        onView(withId(R.id.date_from)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 8 + 1, 1));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.date_from_text)).check(matches(withText("2017-09-01")));
    }
}
