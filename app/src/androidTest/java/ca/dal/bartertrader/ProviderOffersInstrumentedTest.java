package ca.dal.bartertrader;


import androidx.test.ext.junit.rules.ActivityScenarioRule;

import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
public class ProviderOffersInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() throws InterruptedException {
        onView(withHint("Email"))
                .perform(click())
                .perform(typeText("eliask1094@gmail.com"))
                .perform(closeSoftKeyboard());

        onView(withHint("Password"))
                .perform(click())
                .perform(typeText("123123Abc/!"))
                .perform(closeSoftKeyboard());

        onView(withText("LOG IN"))
                .perform(click());

        Thread.sleep(3000);
        onView(withId(R.id.view_provider_offers)).perform(click());
        Thread.sleep(3000);
    }

    @Test
    public void checkReceiverTitleDisplayed() {
        onView(allOf(withId(R.id.receiver_title), withText("Offer: room2"))).check(matches(isDisplayed()));
    }


    @Test
    public void checkProviderTitleDisplayed() {
        onView(withText("Your post: Test item")).check(matches(isDisplayed()));
    }

}