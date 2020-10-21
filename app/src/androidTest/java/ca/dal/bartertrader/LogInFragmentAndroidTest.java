package ca.dal.bartertrader;

import androidx.annotation.NonNull;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;

import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LogInFragmentAndroidTest {
    @Rule
    public ActivityScenarioRule<MainActivity> myRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void logInValidation() {
        onView(withHint("Email"))
                .perform(click())
                .perform(typeText("-nicholasmcphee45@gmail.com"))
                .perform(closeSoftKeyboard());

        onView(withHint("Password"))
                .perform(click())
                .perform(typeText("A@4#_2=01)"))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.logInBtn))
                .perform(click());

        onView(withText("Invalid email or password"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void resetPassword() {
        onView(withText("Reset Password"))
                .perform(click());

        onView(withId(R.id.resetPasswordLayout))
                .check(matches(isDisplayed()));
    }

    @Test
    public void signUp() {
        onView(withText("Sign Up"))
                .perform(click());

        onView(withId(R.id.signUpLayout))
                .check(matches(isDisplayed()));
    }
}