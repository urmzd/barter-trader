package ca.dal.bartertrader.presentation.view.receiver_home;

import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.material.card.MaterialCardView;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.dal.bartertrader.BarterTraderInjector;
import ca.dal.bartertrader.R;
import ca.dal.bartertrader.data.model.FirebasePostModel;
import ca.dal.bartertrader.presentation.view.LoginFragment;
import ca.dal.bartertrader.presentation.view.provider_home.HandlePostFragment;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isFocused;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.EasyMock2Matchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;


@RunWith(AndroidJUnit4.class)
public class ReceiverHomeFragmentTest {

    public static ViewAction typeSearchViewText(final String text, final boolean submit){
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
                ((SearchView) view).setQuery(text,submit);
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

    @Test
    public void testEditableSearchViewExpands() {
        onView(withId(R.id.receiver_search)).perform(click());
        onView(isAssignableFrom(SearchView.class)).check(matches(isEnabled()));
        onView(isAssignableFrom(SearchView.class)).perform(typeSearchViewText("UNIQUE123", false));
        onView(isAssignableFrom(AutoCompleteTextView.class)).check(matches(withText("UNIQUE123")));
    }

    @Test
    public void testOneSearchResultContainsQuery() throws InterruptedException {
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()));
        Thread.sleep(5000);
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));

        onView(withId(R.id.receiver_search)).perform(click());
        onView(isAssignableFrom(SearchView.class)).perform(typeSearchViewText("UNIQUE123", true));
        onView(isAssignableFrom(SearchView.class)).perform(pressKey(KeyEvent.KEYCODE_ENTER));


        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()));
        Thread.sleep(5000);
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));

        onView(ViewMatchers.withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText("UNIQUE123"))));
    }

}