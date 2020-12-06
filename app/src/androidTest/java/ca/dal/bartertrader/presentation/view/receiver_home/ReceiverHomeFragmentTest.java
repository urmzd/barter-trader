package ca.dal.bartertrader.presentation.view.receiver_home;

import android.view.View;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.dal.bartertrader.BarterTraderInjector;
import ca.dal.bartertrader.R;
import ca.dal.bartertrader.presentation.view.LoginFragment;
import ca.dal.bartertrader.presentation.view.provider_home.HandlePostFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isFocused;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
public class ReceiverHomeFragmentTest {
    public static ViewAction typeSearchViewText(final String text){
        return new ViewAction(){
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(SearchView.class));
            }

            @Override
            public String getDescription() {
                return "Search View Text Entered";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((SearchView) view).setQuery(text,false);
            }
        };
    }

    @Before
    public void setUp() throws Exception {
        TestNavHostController navController = new TestNavHostController(
                ApplicationProvider.getApplicationContext());

        FragmentScenario<ReceiverHomeFragment> fragmentScenario = FragmentScenario.launchInContainer(ReceiverHomeFragment.class, null, new FragmentFactory() {
            @NonNull
            @Override
            public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
                ReceiverHomeFragment receiverHomeFragment = new ReceiverHomeFragment(new BarterTraderInjector().getReceiverHomeViewModelFactory());
                receiverHomeFragment.getViewLifecycleOwnerLiveData().observeForever(lifecycleOwner -> {
                    if (lifecycleOwner != null) {
                        Navigation.setViewNavController(receiverHomeFragment.requireView(), navController);
                    }
                });
                return receiverHomeFragment;
            }
        });
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testEditableSearchViewExpands() {
        onView(withId(R.id.receiver_search)).perform(click());
        onView(isAssignableFrom(SearchView.class)).check(matches(isEnabled()));
        onView(isAssignableFrom(SearchView.class)).perform(typeSearchViewText("Urmzd"));
        onView(isAssignableFrom(AutoCompleteTextView.class)).check(matches(withText("Urmzd")));
    }
}