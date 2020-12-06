package ca.dal.bartertrader.presentation.view.receiver_home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import ca.dal.bartertrader.BarterTraderInjector;
import ca.dal.bartertrader.R;
import ca.dal.bartertrader.presentation.view.LoginFragment;
import ca.dal.bartertrader.presentation.view.provider_home.HandlePostFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ReceiverHomeFragmentTest {

    @Test
    public void testSomething() {
        // Create a TestNavHostController
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

}